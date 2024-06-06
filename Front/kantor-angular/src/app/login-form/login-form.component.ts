import {Component, EventEmitter, Output} from '@angular/core';
import {AxiosService} from "../axios.service";
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Title} from '@angular/platform-browser';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent {
  constructor(private axiosService: AxiosService, private router: Router, private snackBar: MatSnackBar, private titleService: Title) {
  }

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
      const message = 'Puste pole!';
      const width = this.calculateSnackbarWidth(message);
      const widthClass = `width-${Math.min(300, Math.max(100, Math.round(width / 100) * 100))}`; // Round to nearest 100, min 100, max 300
      this.snackBar.open(message, '', {
        duration: 3000,
        panelClass: ['error-snackbar', widthClass],
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
      return;
    }

    this.axiosService.request(
      "POST",
      "/api/v1/auth/login",
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
        panelClass: ['register-error-snackbar'],
      });
    });
  }

  calculateSnackbarWidth(text: string): number {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    if (context) {
      context.font = getComputedStyle(document.body).font;
      return context.measureText(text).width;
    }
    return 0;
  }
}
