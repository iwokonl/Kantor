// settings.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../user.service';
import { AxiosService } from '../axios.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit, OnDestroy {
  user_name: string = '';
  firstname: string = '';
  lastname: string = '';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;

  constructor(private axiosService: AxiosService, private userService: UserService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.updateUserDetails();
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      this.updateUserDetails();
    });
  }

  updateUserDetails(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("POST",
        "api/authorization/userinfo",
        {}).then((response) => {
        this.user_name = response.data.username;
        this.firstname = response.data.firstName;
        this.lastname = response.data.lastName;
        console.log(this.firstname);
        console.log(this.lastname);
        console.log(this.user_name);
      });
    }
  }
  updateUserOnServer(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("PUT", "api/authorization/userinfo", {
        username: this.user_name,
        firstName: this.firstname,
        lastName: this.lastname
      }).then((response) => {
        console.log(response);
      });
    }
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
