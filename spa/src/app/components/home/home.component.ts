import { Component } from '@angular/core';
import {Router} from "@angular/router";
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
    private router: Router
  ) { }

  equipment: Array<Equipment> = [];

  isUser: boolean = false;
  isLoggedIn: boolean = false;
  login: string|null = null;

  message: string|null = null;
  messageVisible: boolean = true;

  ngOnInit(): void {
    this.equipmentService.getAll().subscribe(response => {
      if(response.status == 200 && response.body != null) {
        this.equipment = response.body;
      }
    })
    this.message = localStorage.getItem('message');
    let that = this;
    setTimeout(() => {
      that.messageVisible = false;
    }, 5000)
    this.isUser = this.authService.isUserInRole("CLIENT");
    this.isLoggedIn = this.authService.isUserLoggedIn();
    this.login = this.authService.getLogin();
  }

  onLogout() {
    this.authService.logout();
    window.location.reload();
  }

  onMakeRequest(equipment: Equipment) {
    this.router.navigate(['make-reservation', equipment.entityId]);
  }

  empty(message: string|null) {
    if(message == null) {
      return true;
    }
    return message.length == 0;
  }
}
