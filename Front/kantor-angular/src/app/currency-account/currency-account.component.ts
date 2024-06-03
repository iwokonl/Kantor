import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import { CurrencyService } from '../currency.service';
import { CurrencyFlagsService } from '../currency-flags.service';
import { Title } from '@angular/platform-browser';

interface CurrencyFlags {
  [key: string]: string;
}

@Component({
  selector: 'app-currency-account',
  templateUrl: './currency-account.component.html',
  styleUrls: ['./currency-account.component.scss']
})
export class CurrencyAccountComponent implements OnInit {
  accounts: any[] = [];
  isLoading: boolean = false;
  currency: any;
  currencyFlags: { [key: string]: string } = {};
  currencyCode: string = '';
  currencyName: string = '';

  selectedAccount: any = null; // Add this line


  constructor(private axiosService: AxiosService, private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService, private titleService: Title) {
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();
  }

  getCurrencyData() {
    this.axiosService.getCurrencyData('62').then(data => {
      this.currencyCode = data.code;
      this.currencyName = data.name;
    });
  }

  selectAccount(account: any) {
    this.selectedAccount = account;
    this.openAccountForm = false; // Close the openAccountForm when an account is selected
  }

  openAccountForm: boolean = false; // Add this line
  openCurrencyAccountForm() {
    this.openAccountForm = true;
    this.selectedAccount = null; // Close the selectedAccount when the form is opened
  }


  getCurrencyAccounts(): void {
    this.axiosService.request("POST",
      "/api/v1/currencyAccounts/getCurrencyAccounts",
      {}).then((response) => {
      this.accounts = response.data;
      this.accounts.forEach(account => {
        this.axiosService.getCurrencyData(account.currencyId).then(data => {
          account.currencyCode = data.code;
          account.currencyName = data.name;
        });
        if (account.currencyId !== "62") {
          this.currencyService.getCurrencyDetailsByID(account.currencyId).subscribe(details => {
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
    this.axiosService.request("POST", "/api/v1/currencyAccounts/createCurrencyAccountt", newAccount).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account
    });
  }
  ngOnInit(): void {
    this.getCurrencyAccounts();
    this.titleService.setTitle("Konta walutowe - Kantor $€££")
  }


  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }

  onDeleteAccount(account: any) {
    this.axiosService.request("DELETE",
      "/api/v1/currencyAccounts/deleteCurrencyAccount",
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
    account.isLoading = true;
    this.axiosService.request("POST",
      "/api/v1/paypal/create",
      {
        total: account.amount,
        currency: account.currencyId,
        method: "paypal",
        intent: "sale",
        description: "Płatność za pomocą PayPal. Dodanie środków do konta walutowego. Kwota: " + account.amount + " " + account.currencyCode,
        cancelUrl: "http://localhost:8222/api/v1/paypal/cancel",
        successUrl: "http://localhost:8222/api/v1/paypal/success"

      }).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts();
      window.location.href = response.data.url;


      setTimeout(() => {
        account.isLoading = false;
        // Wyłącz animację ładowania po przeniesieniu
      }, 2000000);
    }).catch((error) => {
      account.isLoading = false;

    });
  }

  createPayout(account: any) {
    console.log(account.amount);
    let regExp:RegExp = /[a-zA-Z]/g;
    if (account.amount === undefined || regExp.test(account.amount)) {
      console.log("Invalid amount");
      return;
    }
    account.isLoading = true;
    this.axiosService.request("POST",
      "/api/v1/paypal/createPayout",
      {
        receiverEmail: "kupujacy@kantrol.pl", // Dodać kiedyś do db pole paypal email i przypisać do usera podczas rejestracji.
        total: account.amount,
        currencyId: account.currencyId
      }).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account

      window.location.href = response.data.url;
      setTimeout(() => {
        account.isLoading = false;
        // Wyłącz animację ładowania po przeniesieniu
      }, 2000000);
    }).catch((error) => {
      account.isLoading = false;

    });;

  }
}
