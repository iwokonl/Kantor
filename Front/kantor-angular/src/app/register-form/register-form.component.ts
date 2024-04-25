import {Component} from '@angular/core';
import {AxiosService} from "../axios.service";
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent {
  constructor(private axiosService: AxiosService, private router: Router, private snackBar: MatSnackBar) {
  }

  firstName: string = '';
  lastName: string = '';
  email: string = '';
  login: string = '';
  password: string = '';

  onSubmitRegister() {
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
      this.snackBar.open('Rejestracja nie powiodła się!', '', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
    });
  }
}
