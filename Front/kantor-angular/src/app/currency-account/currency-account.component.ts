import {Component, OnInit, SimpleChanges} from '@angular/core';
import {AxiosService} from '../axios.service';
import {CurrencyService} from '../currency.service';
import {CurrencyFlagsService} from '../currency-flags.service';
import {Title} from '@angular/platform-browser';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormControl} from '@angular/forms';


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

  transactions: any[] = [];

  expectedPrice: number = 0;

  amountControl = new FormControl('');


  constructor(private axiosService: AxiosService, private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService, private titleService: Title, private snackBar: MatSnackBar) {
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
    this.openAccountForm = false;
    this.axiosService.getTransactions(account.currencyId).then(data => {
      this.transactions = data;
    });
    this.amountControl.reset();
  }

  calculateExpectedPrice() {
    if (this.selectedAccount && this.selectedAccount.amount) {
      this.currencyService.getCurrencyDetails(this.selectedAccount.currencyCode).subscribe(details => {
        console.log('details.rates[0].mid:', details.rates[0].mid);
        this.expectedPrice = this.selectedAccount.amount * details.rates[0].mid;
      });
    } else {
      this.expectedPrice = 0;
    }
  }

  openAccountForm: boolean = false;

  openCurrencyAccountForm() {
    this.openAccountForm = true;
    this.selectedAccount = null;
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
      // If an account is selected, find the updated account data and update selectedAccount
      if (this.selectedAccount) {
        const updatedAccount = this.accounts.find(account => account.currencyId === this.selectedAccount.currencyId);
        if (updatedAccount) {
          this.selectedAccount = updatedAccount;
        }
      }
      // czyścimy formularz
      this.amountControl.reset();
      // odświeżenie tabeli transakcji/historii
      if (this.selectedAccount) {
        this.axiosService.getTransactions(this.selectedAccount.currencyId).then(data => {
          this.transactions = data;
        });
      }
      console.log(this.accounts);
    });
  }

  createCurrencyAccount(newAccount: { curencyCode: string, balance: number, userId: number }): void {
    this.axiosService.request("POST", "/api/v1/currencyAccounts/createCurrencyAccountt", newAccount).then((response) => {
      console.log(response.data);
      this.getCurrencyAccounts();
    });
  }

  ngOnInit(): void {
    this.getCurrencyAccounts();
    this.titleService.setTitle("Konta walutowe - Kantor $€££")

    this.amountControl.valueChanges.subscribe(value => {
      if (this.selectedAccount) {
        this.selectedAccount.amount = value;
        this.calculateExpectedPrice();
      }
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedAccount'] && changes['selectedAccount'].currentValue) {
      this.calculateExpectedPrice();
    }
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
    this.selectedAccount = null;
  }

  onAddMoney(account: any) {
    console.log(account.amount);
    let regExp: RegExp = /[a-zA-Z]/g;
    if (account.amount === undefined || regExp.test(account.amount)) {
      console.log("Invalid amount");
      const message = 'Nieprawidłowa kwota!';
      const width = this.calculateSnackbarWidth(message);
      const widthClass = `width-${Math.min(300, Math.max(250, Math.round(width / 100) * 100))}`; // Round to nearest 100, min 100, max 300
      this.snackBar.open(message, '', {
        duration: 3000,
        panelClass: ['error-snackbar', widthClass],
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
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

  // createPayout(account: any) {
  //   console.log(account.amount);
  //   let regExp:RegExp = /[a-zA-Z]/g;
  //   if (account.amount === undefined || regExp.test(account.amount)) {
  //     console.log("Invalid amount");
  //     return;
  //   }
  //   account.isLoading = true;
  //   this.axiosService.request("POST",
  //     "/api/v1/paypal/createPayout",
  //     {
  //       receiverEmail: "kupujacy@kantrol.pl", // Dodać kiedyś do db pole paypal email i przypisać do usera podczas rejestracji.
  //       total: account.amount,
  //       currencyId: account.currencyId
  //     }).then((response) => {
  //     console.log(response.data);
  //     this.getCurrencyAccounts(); // Refresh the accounts list after creating a new account
  //
  //     // window.location.href = response.data.url;
  //     setTimeout(() => {
  //       account.isLoading = false;
  //       // Wyłącz animację ładowania po przeniesieniu
  //     }, 2000000);
  //   }).catch((error) => {
  //     account.isLoading = false;
  //   });;
  // }


  //nowa wersja - animacja trwa tyle ile trwa operacja, po zakończeniu animacja znika, konta sa odswiezane
  createPayout(account: any) {
    console.log(account.amount);
    let regExp: RegExp = /[a-zA-Z]/g;
    if (account.amount === undefined || regExp.test(account.amount)) {
      console.log("Invalid amount");
      const message = 'Nieprawidłowa kwota!';
      const width = this.calculateSnackbarWidth(message);
      const widthClass = `width-${Math.min(300, Math.max(250, Math.round(width / 100) * 100))}`; // Round to nearest 100, min 100, max 300
      this.snackBar.open(message, '', {
        duration: 3000,
        panelClass: ['error-snackbar', widthClass],
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
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

      const message = 'Pomyślnie wypłacono środki!';
      const width = this.calculateSnackbarWidth(message);
      const widthClass = `width-${Math.min(300, Math.max(250, Math.round(width / 100) * 100))}`; // Round to nearest 100, min 100, max 300
      this.snackBar.open(message, '', {
        duration: 3000,
        panelClass: ['success-snackbar', widthClass],
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });

      this.getCurrencyAccounts();
    }).catch((error) => {
      console.log(error);

      // Open the snackbar with the error message
      const message = 'Niewystaczające środki!';
      const width = this.calculateSnackbarWidth(message);
      const widthClass = `width-${Math.min(300, Math.max(250, Math.round(width / 100) * 100))}`; // Round to nearest 100, min 100, max 300
      this.snackBar.open(message, '', {
        duration: 3000,
        panelClass: ['error-snackbar', widthClass],
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });

    }).finally(() => {
      account.isLoading = false;

    });
  }


  calculateSnackbarWidth(text: string): number {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    if (context) {
      context.font = getComputedStyle(document.body).font;
      return context.measureText(text).width;
    }
    return 0;
  }

}
