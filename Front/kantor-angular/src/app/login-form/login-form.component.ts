import {Component, EventEmitter, Output} from '@angular/core';
import {AxiosService} from "../axios.service";
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent {
  constructor(private axiosService: AxiosService, private router: Router, private snackBar: MatSnackBar, private titleService: Title) { }

  login: string = '';
  password: string = '';

  ngOnInit() {
    this.titleService.setTitle('Logowanie - Kantor $€££');
  }

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }

  onSubmitLogin() {
    if (this.login.trim() === '' || this.password.trim() === '') {
      this.snackBar.open('Puste pole!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
      return;
    }

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
