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
  email: string = '';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;
  isEditingUsername: boolean = false;

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
        "api/v1/auth/userinfo",
        {}).then((response) => {
        this.user_name = response.data.username;
        this.firstname = response.data.firstName;
        this.lastname = response.data.lastName;
        this.email = response.data.email;
        console.log(this.firstname);
        console.log(this.lastname);
        console.log(this.user_name);
        console.log(this.email)
      });
    }
  }
  updateUserOnServer(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("PUT", "api/v1/auth/userinfo", {
        username: this.user_name,
        firstName: this.firstname,
        lastName: this.lastname
      }).then((response) => {
        console.log(response);
      });
    }
  }
  toggleEditUsername() {
    this.updateUserDetails();
    this.isEditingUsername = !this.isEditingUsername;
  }
  isEditingEmail = false; // Zmienna kontrolująca stan pola formularza

  toggleEditEmail() {
    this.isEditingEmail = !this.isEditingEmail; // Przełączanie stanu pola formularza

    // if (!this.isEditingEmail) {
    //   // Jeśli pole formularza jest wyłączone, wysyłamy dane do serwera
    //   this.sendDataToServer(this.email);
    // }
  }
  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
