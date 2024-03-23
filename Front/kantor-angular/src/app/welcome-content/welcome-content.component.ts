import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
@Component({
  selector: 'app-welcome-content',
  templateUrl: './welcome-content.component.html',
  styleUrl: './welcome-content.component.scss'
})
export class WelcomeContentComponent implements OnInit{
  user_name: string= '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsernameFromBackend().subscribe(username => {
      this.user_name = username;
    });
  }
}
