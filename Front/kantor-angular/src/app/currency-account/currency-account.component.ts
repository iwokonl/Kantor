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

  getCurrencyAccounts(): void {
    this.axiosService.request("POST",
      "/api/ForeignCurrencyAccount/getCurrencyAccounts",
      {}).then((response) => {
      this.accounts = response.data;
      console.log(this.accounts);
    });
  }

  createCurrencyAccount(newAccount: { curencyCode: string, balance: number, userId: number }): void {
    this.axiosService.request("POST", "/api/ForeignCurrencyAccount/createCurrencyAccount", newAccount).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account
    });
  }

  // createCurrencyAccount(): void {
  //   let newAccount = {
  //     curencyCode: 'USD',
  //     balance: 1000,
  //     userId: 2
  //   };
  //
  //   this.axiosService.request("POST", "/api/ForeignCurrencyAccount/createCurrencyAccount", newAccount).then((response) => {
  //     console.log(response.data);
  //   });
  // }

  ngOnInit(): void {
    this.getCurrencyAccounts();
    // this.createCurrencyAccount()


    // this.axiosService.requestWithOutData(
    //   "POST",
    //   "/api/ForeignCurrencyAccount/getCurrencyAccounts"
    // ).then((response) => {
    //   this.axiosService.getAuthTocken();
    //   this.accounts = response.data;
    //   console.log(this.accounts);
    //
    // });

  }
}
