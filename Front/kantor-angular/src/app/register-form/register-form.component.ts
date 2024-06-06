import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AxiosService } from "../axios.service";
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit, OnDestroy {
  firstName: string = '';
  lastName: string = '';
  email: FormControl;
  login: string = '';
  password: string = '';
  passwordVerification: string = '';

  constructor(
    private axiosService: AxiosService,
    private router: Router,
    private snackBar: MatSnackBar,
    private titleService: Title
  ) {
    // Initialize FormControl with validators, including the custom pattern validator
    this.email = new FormControl('', [
      Validators.required,
      Validators.email,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    ]);
  }

  ngOnInit() {
    this.titleService.setTitle('Rejestracja - Kantor $€££');
  }

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }

  onSubmitRegister() {
    if (this.email.invalid) {
      this.snackBar.open('Błąd adresu e-mail!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
      return;
    }

    if (this.password !== this.passwordVerification) {
      this.snackBar.open('Hasła się nie zgadzają!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
      return;
    }

    this.axiosService.request(
      "POST",
      "/api/v1/auth/register",
      {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email.value,
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
      this.snackBar.open('Rejestracja nie powiodła się!\n', '', {
        duration: 3000,
        panelClass: ['register-error-snackbar'],
      });
    });
  }
}
