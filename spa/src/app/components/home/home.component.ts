import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {EquipmentService} from "../../service/equipment.service";
import {Equipment} from "../../model/Equipment";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(
    private equipmentService: EquipmentService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  equipment: Array<Equipment> = [];

  ngOnInit(): void {
    this.equipmentService.getAll().subscribe(response => {
      if(response.status == 200 && response.body != null) {
        this.equipment = response.body;
      }
    })
  }

  onLogin() {
    this.router.navigate(['login']);
  }

  onLogout() {
    console.log("logout");
  }

}
