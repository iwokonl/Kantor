import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import {User} from "../models/user";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
@Component({
  selector: 'app-welcome-content',
  templateUrl: './welcome-content.component.html',
  styleUrl: './welcome-content.component.scss'
})
export class WelcomeContentComponent implements OnInit{
  user?: User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsernameFromBackend().subscribe((response: HttpResponse<User>) => {
      this.user = response.body!;
    },
      (error: HttpErrorResponse) => {
        console.log(error);
      })
  }
}
