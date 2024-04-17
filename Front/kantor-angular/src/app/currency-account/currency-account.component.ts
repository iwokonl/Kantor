import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-currency-account',
  templateUrl: './currency-account.component.html',
  styleUrls: ['./currency-account.component.scss']
})
export class CurrencyAccountComponent implements OnInit {
  accounts: any[] = [];

  constructor(private axiosService: AxiosService) { }

  ngOnInit(): void {
    this.axiosService.requestWithOutData(
      "POST",
      "/api/ForeignCurrencyAccount/getCurrencyAccounts"
    ).then((response) => {
      this.axiosService.getAuthTocken();
      this.accounts = response.data;
      console.log(this.accounts);
    });
  }
}
