import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../axios.service";

@Component({
  selector: 'app-contents',
  templateUrl: './contents.component.html',
  styleUrl: './contents.component.scss'
})
// implements OnInit
export class ContentsComponent {
  componentToShow: string = "welcome";

  constructor(private axiosService: AxiosService) {
  }


  // Nie wiem co to robi ale buja
  // ngOnInit() {
  //   if (!this.getCookie('firstVisit')) {
  //     this.clearAllCookies();
  //     this.setCookie('firstVisit', 'true', 7); // Ustawia ciasteczko na 7 dni
  //   }
  // }
  //
  // clearAllCookies() {
  //   const cookies = document.cookie.split(";");
  //
  //   for (let i = 0; i < cookies.length; i++) {
  //     const cookie = cookies[i];
  //     const eqPos = cookie.indexOf("=");
  //     const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
  //     document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
  //   }
  // }
  //
  // getCookie(cname: string) {
  //   let name = cname + "=";
  //   let decodedCookie = decodeURIComponent(document.cookie);
  //   let ca = decodedCookie.split(';');
  //   for(let i = 0; i <ca.length; i++) {
  //     let c = ca[i];
  //     while (c.charAt(0) == ' ') {
  //       c = c.substring(1);
  //     }
  //     if (c.indexOf(name) == 0) {
  //       return c.substring(name.length, c.length);
  //     }
  //   }
  //   return "";
  // }
  //
  // setCookie(cname: string, cvalue: string, exdays: number) {
  //   let d = new Date();
  //   d.setTime(d.getTime() + (exdays*24*60*60*1000));
  //   let expires = "expires="+ d.toUTCString();
  //   document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  // }
  onRegister(input: any) {
    this.axiosService.request(
      "POST",
      "/register",
      {
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        username: input.login,
        password: input.password

      }

    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.componentToShow = "messages";
    });

  }
}
