import {Component} from '@angular/core';
import {AxiosService} from "../axios.service";
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent {
  constructor(private axiosService: AxiosService, private router: Router, private snackBar: MatSnackBar, private titleService: Title) {
  }

  firstName: string = '';
  lastName: string = '';
  email: string = '';
  login: string = '';
  password: string = '';
  passwordVerification: string = '';


  ngOnInit() {
    this.titleService.setTitle('Rejestracja - Kantor $€££');
  }

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }

  onSubmitRegister() {
    if (this.firstName.trim() === '' || this.lastName.trim() === '' || this.email.trim() === '' || this.login.trim() === '' || this.password.trim() === '' || this.passwordVerification.trim() === '') {
      this.snackBar.open('Wszystkie pola muszą być wypełnione!', '', {
        duration: 5000,
        panelClass: ['register-error-snackbar'],
      });
      return;
    }

    if (this.password !== this.passwordVerification) {
      this.snackBar.open('Hasła nie zgadzają się!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
      return;
    }

    this.axiosService.request(
      "POST",
      "/api/authorization/register",
      {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        username: this.login,
        password: this.password
      }
    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.snackBar.open('Pomyślnie zarejestrowano!', '', {
        duration: 3000,
        panelClass: ['success-snackbar'],
      });
      this.router.navigate(['/login']);
    }).catch((error) => {
      // Handle registration error
      this.snackBar.open('Rejestracja nie powiodła się!\n', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
    });
  }
}
