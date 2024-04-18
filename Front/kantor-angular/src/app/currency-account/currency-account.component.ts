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
  isLoading: boolean = false;
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

  onDeleteAccount(account: any) {
    this.axiosService.request("DELETE",
      "/api/ForeignCurrencyAccount/deleteCurrencyAccount",
      {
        id: account.id
      }).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account
    });
  }

  onAddMoney(account: any) {
    console.log(account.amount);
    let regExp:RegExp = /[a-zA-Z]/g;
    if (account.amount === undefined || regExp.test(account.amount)) {
      console.log("Invalid amount");
      return;
    }
    this.isLoading = true;
    this.axiosService.request("POST",
      "/api/payment/create",
      {
        total: account.amount,
        currency: account.curencyCode,
        method: "paypal",
        intent: "sale",
        description: "Płatność za pomocą PayPal. Dodanie środków do konta walutowego. Kwota: " + account.amount + " " + account.currencyCode,
        cancelUrl: "http://localhost:8082/api/payment/cancel",
        successUrl: "http://localhost:8082/api/payment/success"

      }).then((response) => {
      console.log(response.data);
      window.location.href = response.data.url;
      this.getCurrencyAccounts();
      setTimeout(() => {
        this.isLoading = false; // Wyłącz animację ładowania po przeniesieniu
      }, 200000000);
    }).catch((error) => {
      this.isLoading = false;
    });
  }

  createPayout(account: any) {
    console.log(account.amount);
    let regExp:RegExp = /[a-zA-Z]/g;
    if (account.amount === undefined || regExp.test(account.amount)) {
      console.log("Invalid amount");
      return;
    }
    this.isLoading = true;
    this.axiosService.request("POST",
      "/api/payment/createPayout",
      {
        receiverEmail: "kupujacy@kantrol.pl", // Dodać kiedyś do db pole paypal email i przypisać do usera podczas rejestracji.
        total: account.amount,
        currency: account.curencyCode
      }).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account

      window.location.href = response.data.url;
      setTimeout(() => {
        this.isLoading = false; // Wyłącz animację ładowania po przeniesieniu
      }, 200000000);
    }).catch((error) => {
      this.isLoading = false;
    });;

  }
}
