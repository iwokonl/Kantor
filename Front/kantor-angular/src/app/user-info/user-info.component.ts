import { Component } from '@angular/core';
import {AxiosService} from "../axios.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.scss'
})
export class UserInfoComponent {
  constructor(private axiosService: AxiosService, private router: Router) {
  }
  login: string = '';
  password: string = '';
  onSubmitUserInfo() {
    this.axiosService.requestWithOutData(
      "POST",
      "/api/authorization/userinfo"
    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);

    });
  }
}
