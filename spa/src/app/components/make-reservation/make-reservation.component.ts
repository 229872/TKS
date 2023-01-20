import { Component } from '@angular/core';
import {EquipmentService} from "../../service/equipment.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Equipment} from "../../model/Equipment";
import {FormBuilder, Validators} from "@angular/forms";
import {Rent} from "../../model/Rent";
import {Client} from "../../model/User";
import {ClientService} from "../../service/client.service";
import {RentService} from "../../service/rent.service";

@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.css']
})
export class MakeReservationComponent {

  constructor(
    private equipmentService: EquipmentService,
    private clientService: ClientService,
    private rentService: RentService,
    private authService: AuthService,
    private activeRoute: ActivatedRoute,
    private fb : FormBuilder,
    private router: Router
  ) { }

  uuid: string = '';
  equipment: Equipment|null = null;
  client: Client|null = null;
  rentStatus: RentStatus = RentStatus.NotCreated;

  reservationForm = this.fb.group({
    beginTime: ['', [
      Validators.required,
      Validators.pattern(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/)
    ]],
    endTime: ['', [
      Validators.required,
      Validators.pattern(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/)
    ]],
  })

  get beginTime() {
    return this.reservationForm.controls['beginTime'];
  }

  get endTime() {
    return this.reservationForm.controls['endTime'];
  }

  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((params) => {
      this.uuid = params.get('id')!.toString();
      this.equipmentService.getByUUID(this.uuid).subscribe(result => {
        if (result.status == 200) {
          this.equipment = result.body;
        } else {
          // equipment not found
        }
      })
    });
    // @ts-ignore
    this.clientService.getByLogin(this.authService.getLogin()).subscribe(result => {
      if(result.status == 200) {
        this.client = result.body;
      }
    })
    let now = new Date();
    let month: String = String(now.getMonth() + 1);
    let day: String = String(now.getDate() + 1);
    this.beginTime.setValue(`${now.getFullYear()}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`);
  }

  isConflict() {
    return this.rentStatus == RentStatus.Conflict;
  }

  isBad() {
    return this.rentStatus == RentStatus.BadRequest;
  }

  onSubmit() {
    let beginTime = this.reservationForm.getRawValue().beginTime + 'T00:00:00';
    let endTime = this.reservationForm.getRawValue().endTime + 'T00:00:00';

    const rent: Rent = {
      equipmentUUID: this.equipment?.entityId,
      clientUUID: this.client?.entityId,
      beginTime, endTime
    } as Rent;
    this.rentService.createRent(rent).subscribe(result => {
      if(result.status == 200) {
        this.rentStatus = RentStatus.Good;
        localStorage.setItem('message', 'Pomyślnie utworzono rezerwację!')
        this.router.navigate(['/']);
      }
    }, error => {
      console.log(error);
      if(error.status == 409) {
        this.rentStatus = RentStatus.Conflict;
      } else if(error.status == 400) {
        this.rentStatus = RentStatus.BadRequest;
      }
    })
  }
}

export enum RentStatus {
  NotCreated,
  Good,
  Conflict,
  BadRequest
}
