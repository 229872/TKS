import { Component } from '@angular/core';
import {EquipmentService} from "../../service/equipment.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Equipment} from "../../model/Equipment";
import {ClientService} from "../../service/client.service";
import {Client} from "../../model/User";
import {FormBuilder, Validators} from "@angular/forms";
import {
  NgbDate,
  NgbDateAdapter,
  NgbDateNativeUTCAdapter, NgbDateStructAdapter,
  NgbModal,
  NgbModalRef
} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.css'],
  providers: [{ provide: NgbDateAdapter, useClass: NgbDateNativeUTCAdapter }]
})
export class MakeReservationComponent {

  constructor(
    private equipmentService: EquipmentService,
    private clientService: ClientService,
    private authService: AuthService,
    private activeRoute: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) { }

  uuid: string = '';
  equipment: Equipment|null = null;
  client: Client|null = null;

  makeReservationForm = this.fb.group({
    beginTime: [new Date(), [Validators.required]],
    endTime: [new Date(), [Validators.required]],
  });

  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((params) => {
      this.uuid = params.get('id')!.toString();
      this.equipmentService.getByUUID(this.uuid).subscribe(result => {
        if(result.status == 200) {
          this.equipment = result.body;
        }
      })
      // @ts-ignore
      this.clientService.getByLogin(this.authService.getLogin()).subscribe(result => {
        if(result.status == 200) {
          this.client = result.body;
        }
      })
    });
  }

  makeRent() {

  }
}
