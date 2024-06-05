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
  id: string = '';
  user_name: string = '';
  firstname: string = '';
  lastname: string = '';
  email: string = '';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;
  isEditingUsername: boolean = false;
  isEditingFirstName: boolean = false;
  isEditingLastName: boolean = false;
  isProfileSelected: boolean = true;
  isSecuritySelected: boolean = false;
  isHelpSelected: boolean = false;

  constructor(private axiosService: AxiosService, private userService: UserService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.updateUserDetails();
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      this.updateUserDetails();
    });
  }

  selectCategory(category: string) {
    this.isProfileSelected = category === 'profile';
    this.isSecuritySelected = category === 'security';
    this.isHelpSelected = category === 'help';
  }

  updateUserDetails(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("POST",
        "api/v1/auth/userInfo",
        {}).then((response) => {
        this.user_name = response.data.username;
        this.firstname = response.data.firstName;
        this.lastname = response.data.lastName;
        this.email = response.data.email;
        this.id = response.data.id;
        console.log(this.firstname);
        console.log(this.lastname);
        console.log(this.user_name);
        console.log(this.email);
        console.log(this.id)
      });
    }
  }
  changeUserLogin(): void {
    this.axiosService.request("PUT", "api/v1/auth/loginChange", {
      username: this.user_name,
      id: this.id
    }).then((response) => {
      console.log(response);
    });
  }
  changeUserFirstName(): void {
    this.axiosService.request("PUT", "api/v1/auth/firstnameChange", {
      firstName: this.firstname,
      id: this.id
    }).then((response) => {
      console.log(response);
    });
  }
  changeUserLastName(): void {
    this.axiosService.request("PUT", "api/v1/auth/lastnameChange", {
      lastName: this.lastname,
      id: this.id
    }).then((response) => {
      console.log(response);
    });
  }

  changeUserEmail(): void {
    this.axiosService.request("PUT", "api/v1/auth/emailChange", {
      email: this.email,
      id: this.id
    }).then((response) => {
      console.log(response);
    });
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
    //   this.changeUserDetails();
    // }
  }

  toggleEditFirstName() {
    this.updateUserDetails();
    this.isEditingFirstName = !this.isEditingFirstName;
  }

  toggleEditLastName() {
    this.updateUserDetails();
    this.isEditingLastName = !this.isEditingLastName;
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
