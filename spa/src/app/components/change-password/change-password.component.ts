import { Component } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {CredentialsChangePassword} from "../../model/Credentials";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  changePasswordForm = this.fb.group({
    oldPassword: ['', [
      Validators.required
    ]],
    newPassword: ['', [
      Validators.required,
      Validators.minLength(8)
    ]]
  });

  incorrectPassword: boolean = false;

  get oldPassword() {
    return this.changePasswordForm.controls['oldPassword'];
  }

  get newPassword() {
    return this.changePasswordForm.controls['newPassword'];
  }

  get newPasswordLength() {
    let newPassword = this.changePasswordForm.getRawValue().newPassword;
    if(newPassword !== null) {
      return newPassword.length;
    }
    return 0;
  }

  onSubmit() {
    const credentials = {
      login: this.authService.getLogin(),
      password: this.changePasswordForm.getRawValue().oldPassword,
      newPassword: this.changePasswordForm.getRawValue().newPassword
    } as CredentialsChangePassword;

    this.authService.changePassword(credentials).subscribe(result => {
      if(result.status == 200) {
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    }, error => {
      this.incorrectPassword = true;
    })
  }
}
