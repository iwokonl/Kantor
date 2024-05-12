import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../user.service';
import { AxiosService } from '../axios.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-welcome-content',
  templateUrl: './welcome-content.component.html',
  styleUrls: ['./welcome-content.component.scss']
})
export class WelcomeContentComponent implements OnInit, OnDestroy {
  user_name: string = '';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;

  constructor(private axiosService: AxiosService, private userService: UserService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.updateUsername();
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      this.updateUsername();
    });
  }

  updateUsername(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("POST",
        "api/v1/auth/userInfo",
        {}).then((response) => {
        this.user_name = response.data.username;
        console.log(this.user_name);
      });
    }
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
