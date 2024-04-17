// welcome-content.component.ts
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
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      if (isLoggedIn) {
        this.axiosService.request("POST",
          "api/authorization/userinfo",
          {}).then((response) => {
          this.user_name = response.data.username;
          console.log(this.user_name);
        });

        // this.userService.getUsernameFromBackend().subscribe(username => {
        //   this.user_name = username;
        // });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
