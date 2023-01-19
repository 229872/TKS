import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {Credentials} from "../../model/Credentials";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  loginForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required]
  });

  get login() {
    return this.loginForm.controls['login'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  onSubmit() {
    console.log("submit");
    if(this.loginForm.getRawValue().login == null &&
      this.loginForm.getRawValue().password == null) {
      return;
    }
    const credentials: Credentials = {
      login: this.loginForm.getRawValue().login,
      password: this.loginForm.getRawValue().password
    } as Credentials;

    this.authService.login(credentials).subscribe(result => {
      console.log(result);
      if(result.status == 200) {
        this.authService.saveUserData(result);
      }
    });
  }
}
