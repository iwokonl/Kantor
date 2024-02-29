import {Component, OnInit} from '@angular/core';
import { AxiosService} from "../axios.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-auth-content',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './auth-content.component.html',
  styleUrl: './auth-content.component.scss'
})
export class AuthContentComponent implements OnInit{
  data: string[] = [];
  constructor(private axiosService: AxiosService) {}

  ngOnInit() {
    this.axiosService.request("GET",
      "/messages",
      {}
    ).then(
      (response) => this.data = response.data

    );
    console.log(1234);
  }
}
