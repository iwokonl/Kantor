import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';
@Component({
  selector: 'app-auth-content',
  templateUrl: './auth-content.component.html',
  styleUrl: './auth-content.component.scss'
})
export class AuthContentComponent {
  data: string[] = [];

  constructor(private axiosService: AxiosService) {

  }
  ngOnInit() {
    this.axiosService.request('GET', '/messages', {})
      .then((response) => {
      this.data = response.data;
    });
  }

  // ngOnInit() {
  //   this.axiosService.request('POST', '/messages', {"name":"chi"})
  //     .then((response) => {
  //       this.data = response.data;
  //     });
  // }

}
