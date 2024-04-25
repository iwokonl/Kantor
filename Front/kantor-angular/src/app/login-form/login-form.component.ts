import {Component, EventEmitter, Output} from '@angular/core';
import {AxiosService} from "../axios.service";
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent {
  constructor(private axiosService: AxiosService, private router: Router, private snackBar: MatSnackBar) { }

  login: string = '';
  password: string = '';

  onSubmitLogin() {
    this.axiosService.request(
      "POST",
      "/api/authorization/login",
      {
        username: this.login,
        password: this.password
      }
    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.snackBar.open('Pomyślnie zalogowano!', '', {
        duration: 3000,
        panelClass: ['success-snackbar'],
      });
      this.router.navigate(['/']);
    }).catch((error) => {
      // Handle login error
      this.snackBar.open('Logowanie nie powiodło się!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
    });
  }
}
