import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import { CurrencyService } from '../currency.service';


@Component({
  selector: 'app-currency-account',
  templateUrl: './currency-account.component.html',
  styleUrls: ['./currency-account.component.scss']
})
export class CurrencyAccountComponent implements OnInit {
  accounts: any[] = [];

  constructor(private axiosService: AxiosService, private currencyService: CurrencyService) { }

  getCurrencyAccounts(): void {
    this.axiosService.request("POST",
      "/api/ForeignCurrencyAccount/getCurrencyAccounts",
      {}).then((response) => {
      this.accounts = response.data;
      this.accounts.forEach(account => {
        if (account.curencyCode !== 'PLN') {
          this.currencyService.getCurrencyDetails(account.curencyCode).subscribe(details => {
            account.balanceInPLN = account.balance * details.rates[0].mid;
          });
        } else {
          account.balanceInPLN = account.balance;
        }
      });
      console.log(this.accounts);
    });
  }

  createCurrencyAccount(newAccount: { curencyCode: string, balance: number, userId: number }): void {
    this.axiosService.request("POST", "/api/ForeignCurrencyAccount/createCurrencyAccount", newAccount).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account
    });
  }
  ngOnInit(): void {
    this.getCurrencyAccounts();

  }
}
