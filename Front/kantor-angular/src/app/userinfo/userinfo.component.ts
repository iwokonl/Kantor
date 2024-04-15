import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-userinfo',
  templateUrl: './userinfo.component.html',
  styleUrls: ['./userinfo.component.scss']
})
export class UserinfoComponent implements OnInit {
  constructor(private axiosService: AxiosService) {}

  ngOnInit(): void {
    this.axiosService.requestUserInfo()
      .then(response => {
        const jwtToken = response.data;
        this.axiosService.setAuthTocken(jwtToken);
      })
      .catch(error => {
        console.error('Error fetching JWT token:', error);
      });
  }
}
