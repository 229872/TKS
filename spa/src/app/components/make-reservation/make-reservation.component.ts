import { Component } from '@angular/core';
import {EquipmentService} from "../../service/equipment.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Equipment} from "../../model/Equipment";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.css']
})
export class MakeReservationComponent {

  constructor(
    private equipmentService: EquipmentService,
    private authService: AuthService,
    private activeRoute: ActivatedRoute,
    private fb : FormBuilder,
    private router: Router
  ) { }

  uuid: string = '';
  equipment: Equipment|null = null;

  reservationForm = this.fb.group({
    beginTime: ['', [
      Validators.required,
      Validators.pattern(/^[0-9]{4} [0-9]{2} [0-9]{2}$/)
    ]],
    endTime: ['', [
      Validators.required,
      Validators.pattern(/^[0-9]{4} [0-9]{2} [0-9]{2}$/)
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
        if(result.status == 200) {
          this.equipment = result.body;
        }
      })
    });
  }

  onSubmit() {

  }
}
