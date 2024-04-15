import { Component } from '@angular/core';
import {AxiosService} from "../axios.service";
import {Router} from "@angular/router";
import * as emoji from 'node-emoji'
@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.scss'
})
export class UserInfoComponent {
  constructor(private axiosService: AxiosService, private router: Router) {
  }
  userInfo: any = null;
  login: string = '';
  password: string = '';
  onSubmitUserInfo() {
    console.log(emoji.find('🇵🇱') )
    this.axiosService.requestWithOutData(
      "POST",
      "/api/authorization/userinfo"
    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.userInfo = response.data;
    });
  }
}
