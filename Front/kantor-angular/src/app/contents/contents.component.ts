import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../axios.service";
import {CurrencyService} from "../header/header.component";
import {CurrencyDto} from "../currency-search.service";
import {FormBuilder} from "@angular/forms";


@Component({
  selector: 'app-contents',
  templateUrl: './contents.component.html',
  styleUrl: './contents.component.scss'
})
export class ContentsComponent {
  componentToShow: string = "welcome";

  constructor(private axiosService: AxiosService) {
  }
  onLogin(input: any) {
    this.axiosService.request(
      "POST",
      "/login",
      {
        username: input.login,
        password: input.password
      }

    ).then((response) => {
      this.axiosService.setAuthTocken(response.data.token);
      this.componentToShow = "messages";
    });

  }

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

export class AppComponent implements OnInit {
  searchValue = '';
  currency: CurrencyDto[]=[];
  searchForm = this.fb.nonNullable.group({
    searchValue: '',
  });
  constructor(private currencyService: CurrencyService,
              private fb: FormBuilder
  ) {}
  ngOnInit() {
    this.fetchData();
  }
  fetchData(): void {
    this.currencyService.getCurrency(this.searchValue).subscribe((currency) => {
      this.currency=currency;
    })
  }
}
