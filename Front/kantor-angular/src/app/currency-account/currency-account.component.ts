import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import * as emoji from "node-emoji";

@Component({
  selector: 'app-currency-account',
  templateUrl: './currency-account.component.html',
  styleUrls: ['./currency-account.component.scss']
})
export class CurrencyAccountComponent implements OnInit {
  accounts: any[] = [];

  constructor(private axiosService: AxiosService) { }

  // ngOnInit(): void {
  //   this.axiosService.request('POST', '/ForeignCurrencyAccount/getCurrencyAccounts', { userId: 1 })
  //     .then(response => {
  //       this.accounts = response.data;
  //     })
  //     .catch(error => {
  //       console.error('Error fetching currency accounts:', error);
  //     });
  // }

  // onSubmitUserInfo() {
  ngOnInit(): void {

    // //TODO - zakomentowane, żeby móc wywołać userinfo. Problem z axiosem
    // this.axiosService.requestWithOutData(
    //   "POST",
    //   "/api/ForeignCurrencyAccount/getCurrencyAccounts"
    // ).then((response) => {
    //   this.axiosService.setAuthTocken(response.data.token);
    //   this.accounts = response.data;
    //   console.log(this.accounts);
    // });

  }
}
