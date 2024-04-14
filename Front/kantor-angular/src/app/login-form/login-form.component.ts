import {Component, EventEmitter, Output} from '@angular/core';
import {AxiosService} from "../axios.service";
import { Router } from '@angular/router';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {
  constructor(private axiosService: AxiosService, private router: Router) {
  }

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
      this.router.navigate(['/currency-account']);

    });
  }

}
