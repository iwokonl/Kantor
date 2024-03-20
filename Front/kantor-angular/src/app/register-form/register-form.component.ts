import {Component, EventEmitter, input, Output} from '@angular/core';
import {AxiosService} from "../axios.service";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent {
  constructor(private axiosService: AxiosService) {
  }
firstName: string = '';
lastName: string = '';
email: string = '';
login: string = '';
password: string = '';

onSubmitRegister() {
  this.axiosService.request(
    "POST",
    "api/authorization/register",
    {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      username: this.login,
      password: this.password

    }

  ).then((response) => {
    this.axiosService.setAuthTocken(response.data.token);
  });
}

}
