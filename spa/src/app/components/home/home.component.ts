import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {EquipmentService} from "../../service/equipment.service";
import {Equipment} from "../../model/Equipment";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(
    private equipmentService: EquipmentService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  equipment: Array<Equipment> = [];

  isUser: boolean = false;
  isLoggedIn: boolean = false;
  login: string|null = null;

  ngOnInit(): void {
    this.equipmentService.getAll().subscribe(response => {
      if(response.status == 200 && response.body != null) {
        this.equipment = response.body;
      }
    })
    this.isUser = this.authService.isUserInRole("CLIENT");
    this.isLoggedIn = this.authService.isUserLoggedIn();
    this.login = this.authService.getLogin();
    console.log(this.authService.getToken());
  }

  onLogout() {
    this.authService.logout();
    window.location.reload();
  }

  onChangePassword() {

  }

}
