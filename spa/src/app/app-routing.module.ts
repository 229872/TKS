import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {MakeReservationComponent} from "./components/make-reservation/make-reservation.component";
import {LoginComponent} from "./components/login/login.component";
import {ChangePasswordComponent} from "./components/change-password/change-password.component";
import {LoggedInGuard} from "./service/auth-guard/logged-in.guard";
import {ClientGuard} from "./service/auth-guard/client.guard";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {
    path: 'make-reservation/:id',
    component: MakeReservationComponent,
    canActivate: [ClientGuard]
  },
  {path: 'login', component: LoginComponent},
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    canActivate: [LoggedInGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
