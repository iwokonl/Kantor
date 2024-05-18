import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../user.service';
import { AxiosService } from '../axios.service';
import {forkJoin, Subscription} from 'rxjs';
import { CurrencyService } from '../currency.service';
import {catchError, map} from "rxjs/operators";
import { of } from 'rxjs';

@Component({
  selector: 'app-welcome-content',
  templateUrl: './welcome-content.component.html',
  styleUrls: ['./welcome-content.component.scss']
})
export class WelcomeContentComponent implements OnInit, OnDestroy {
  user_name: string = '';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;

  exchangeRates: { from: string, to: string, rate: number }[] = []; //do wyświetlania kursów walut na stronie głównej

  // exchangeRatesChanges: { from: string, to: string, rate: number, change: number }[] = []; //do karuzeli walut na stronie głównej
  exchangeRatesChanges: { from: string, to: string, rate: number, change: number, percentageChange?: number }[] = [];

  constructor(private axiosService: AxiosService, private userService: UserService, private currencyService: CurrencyService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.updateUsername();
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      this.updateUsername();
    });

    this.printExchangeRates();
    this.fetchExchangeRatesChanges();
    console.log(this.exchangeRatesChanges);
  }

  updateUsername(): void {
    if (this.isLoggedIn) {
      this.axiosService.request("POST",
        "api/v1/auth/userInfo",
        {}).then((response) => {
        this.user_name = response.data.username;
        console.log(this.user_name);
      });
    }
  }

  printExchangeRates(): void {
    const currencyPairs = [
      { from: 'EUR', to: 'PLN' },
      { from: 'USD', to: 'PLN' },
      { from: 'GBP', to: 'PLN' },
      { from: 'USD', to: 'EUR' },
      { from: 'USD', to: 'GBP' },
      { from: 'USD', to: 'JPY' },
    ];

    currencyPairs.forEach(pair => {
      if (pair.from === 'PLN') {
        // When the base currency is PLN, directly use the rate from the API
        this.currencyService.getCurrencyDetails(pair.to).subscribe(details => {
          this.exchangeRates.push({ from: pair.from, to: pair.to, rate: 1 / details.rates[0].mid });
        });
      } else if (pair.to === 'PLN') {
        // When the target currency is PLN, directly use the rate from the API
        this.currencyService.getCurrencyDetails(pair.from).subscribe(details => {
          this.exchangeRates.push({ from: pair.from, to: pair.to, rate: details.rates[0].mid });
        });
      } else {
        // Otherwise, calculate the exchange rate
        this.currencyService.getExchangeRate(pair.from, pair.to).subscribe(rate => {
          this.exchangeRates.push({ from: pair.from, to: pair.to, rate });
        });
      }
    });
  }

  fetchExchangeRatesChanges(): void {
    const currencies = ['USD', 'EUR', 'GBP', 'CHF', 'JPY', 'CAD', 'AUD', 'CZK', 'DKK', 'NOK', 'SEK'];

    const today = new Date();
    const oneDayAgo = new Date(today);
    oneDayAgo.setDate(today.getDate() -1 );

    const requests = currencies.map(currency =>
      forkJoin({
        todayRate: this.currencyService.getCurrencyDetails(currency).pipe(catchError(error => { console.error('Error fetching todayRate for ' + currency, error); return of(null); })),
        oneDayAgoRate: this.currencyService.getCurrencyHistory(currency, oneDayAgo.toISOString().split('T')[0], today.toISOString().split('T')[0]).pipe(catchError(error => { console.error('Error fetching oneDayAgoRate for ' + currency, error); return of(null); }))
      }).pipe(
        map(({ todayRate, oneDayAgoRate }) => {
          const rate = todayRate.rates[0].mid;
          const oldRate = oneDayAgoRate.rates[0].mid;
          const change = rate - oldRate;
          const percentageChange = (change / oldRate) * 100;

          // console.log('exchangeRateChange for ' + currency + ': ', { from: currency, to: 'PLN', rate, change, percentageChange });
          return { from: currency, to: 'PLN', rate, change, percentageChange };
        })
      )
    );

    forkJoin(requests).subscribe(results => {
      this.exchangeRatesChanges = results;
      console.log(this.exchangeRatesChanges);
    });
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
}
