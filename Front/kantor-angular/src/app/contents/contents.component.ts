import { Component } from '@angular/core';
import {AxiosService} from "../axios.service";

@Component({
  selector: 'app-contents',
  templateUrl: './contents.component.html',
  styleUrl: './contents.component.scss'
})
export class ContentsComponent {
  componentToShow: string = "welcome";

  constructor(private axiosService: AxiosService) {
  }
  onLogin(input: any) {
    this.axiosService.request(
      "POST",
      "/login",
      {
        username: input.login,
        password: input.password
      }

    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.componentToShow = "messages";
    });

  }

  onRegister(input: any) {
    this.axiosService.request(
      "POST",
      "/register",
      {
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        username: input.login,
        password: input.password

      }

    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.componentToShow = "messages";
    });

  }
}
