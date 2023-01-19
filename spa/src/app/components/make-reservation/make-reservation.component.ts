import { Component } from '@angular/core';
import {EquipmentService} from "../../service/equipment.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Equipment} from "../../model/Equipment";

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
    private router: Router
  ) { }

  uuid: string = '';
  equipment: Equipment|null = null;

  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((params) => {
      this.uuid = params.get('id')!.toString();
      this.equipmentService.getByUUID(this.uuid).subscribe(result => {
        if(result.status == 200) {
          this.equipment = result.body;
        }
      })
      this
    });

  }
}
