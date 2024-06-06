import {Component, OnInit, OnDestroy} from '@angular/core';
import {UserService} from '../user.service';
import {AxiosService} from '../axios.service';
import {forkJoin, Subscription} from 'rxjs';
import {CurrencyService} from '../currency.service';
import {catchError, map} from "rxjs/operators";
import {of} from 'rxjs';
import {CurrencyFlagsService} from "../currency-flags.service";

import {Chart} from 'chart.js/auto'; // Import Chart from chart.js
import 'chartjs-adapter-date-fns'; // Import chartjs-adapter-date-fns for time functionality


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

  accounts: any[] = [];
  currencyFlags: { [key: string]: string } = {};

  transactions: any[] = [];

  chart: Chart | undefined;
  startDate: string = '';
  endDate: string = '';

  charts: { [key: string]: Chart } = {};


  constructor(private axiosService: AxiosService, private userService: UserService, private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService) {
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();
  }

  ngOnInit(): void {
    this.getCurrencyAccounts();
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.updateUsername();
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
      this.updateUsername();
    });

    this.fetchTransactions();

    this.printExchangeRates();
    this.fetchExchangeRatesChanges();
    console.log(this.exchangeRatesChanges);

    const endDate = new Date();
    const startDate = new Date();
    startDate.setDate(endDate.getDate() - 30);

    this.startDate = startDate.toISOString().split('T')[0];
    this.endDate = endDate.toISOString().split('T')[0];

    // Fetch the data and create the chart
    this.updateChartData();
  }

  async updateChartData() {
    const currencies = ['USD', 'EUR', 'CHF'];
    const chartIds = ['chartUSD', 'chartEUR', 'chartCHF'];

    for (let index = 0; index < currencies.length; index++) {
      const currency = currencies[index];
      const chartId = chartIds[index];

      // Fetch the history data
      const history = await this.currencyService.getCurrencyHistory(currency, this.startDate, this.endDate).toPromise();

      if (history.rates && Array.isArray(history.rates)) {
        // Prepare the labels and values
        const labels = history.rates.map((rate: any) => rate.effectiveDate);
        const values = history.rates.map((rate: any) => rate.mid);

        // Create the chart
        this.createChart(chartId, labels, values);
      }
    }
  }

  createChart(chartId: string, labels: string[], values: number[]) {
    if (this.charts[chartId]) {
      this.charts[chartId].destroy();
    }
    this.charts[chartId] = new Chart(chartId, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          // label: 'USD',
          data: values,
          borderColor: '#3cba9f',
          fill: false,
          tension: 1,
          cubicInterpolationMode: 'monotone',
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
            onClick: (e, legendItem, legend) => {
            }
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                var label = context.dataset.label || '';

                if (label) {
                  label += ': ';
                }
                if (context.parsed.y !== null) {
                  label += new Intl.NumberFormat('en-US', {style: 'decimal'}).format(context.parsed.y) + ' PLN';
                }
                return label;
              }
            }
          }
        },
        scales: {
          x: {
            type: 'time',
            time: {
              unit: 'day',
              displayFormats: {
                day: 'dd.MM'
              },
              parser: 'yyyy-MM-dd',
              tooltipFormat: 'dd.MM.yyyy',
            },
            ticks: {
              color: '#DBDBDB',
            },
            grid: {
              color: 'rgba(219, 219, 219, 0)'
            }
          },
          y: {
            type: 'linear',
            position: 'left',
            ticks: {
              color: '#DBDBDB'
            },
            grid: {
              color: 'rgba(219, 219, 219, 0.2)',
            }
          }
        }
      }
    });
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

  fetchTransactions(): void {
    this.axiosService.request('GET', '/api/v1/transactions/getTransactionByUser', {}).then((response) => {
      console.log('Response from API:', response); // Log the entire response
      this.transactions = response.data;
      console.log('Transactions data:', this.transactions); // Log the transactions data

      // Replace targetCurrencyId with currencyCode for each transaction
      this.transactions.forEach(transaction => {
        this.axiosService.getCurrencyData(transaction.targetCurrencyId).then(currencyData => {
          transaction.currencyId = currencyData.code;
        }).catch(error => {
          console.error(`Error fetching currency data for id ${transaction.targetCurrencyId}:`, error);
        });
      });
    }).catch((error) => {
      console.error('Error fetching transactions:', error); // Log any error that occurs
    });
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
      {from: 'EUR', to: 'PLN'},
      {from: 'USD', to: 'PLN'},
      {from: 'GBP', to: 'PLN'},
      {from: 'USD', to: 'EUR'},
      {from: 'USD', to: 'GBP'},
      {from: 'USD', to: 'JPY'},
      {from: 'EUR', to: 'USD'},
      {from: 'EUR', to: 'GBP'},
      {from: 'GBP', to: 'USD'},
      {from: 'JPY', to: 'USD'},
      {from: 'CAD', to: 'USD'},
      {from: 'AUD', to: 'USD'},
      {from: 'CHF', to: 'USD'},
      {from: 'HKD', to: 'USD'},
    ];

    currencyPairs.forEach(pair => {
      if (pair.from === 'PLN') {
        // When the base currency is PLN, directly use the rate from the API
        this.currencyService.getCurrencyDetails(pair.to).subscribe(details => {
          this.exchangeRates.push({from: pair.from, to: pair.to, rate: 1 / details.rates[0].mid});
        });
      } else if (pair.to === 'PLN') {
        // When the target currency is PLN, directly use the rate from the API
        this.currencyService.getCurrencyDetails(pair.from).subscribe(details => {
          this.exchangeRates.push({from: pair.from, to: pair.to, rate: details.rates[0].mid});
        });
      } else {
        // Otherwise, calculate the exchange rate
        this.currencyService.getExchangeRate(pair.from, pair.to).subscribe(rate => {
          this.exchangeRates.push({from: pair.from, to: pair.to, rate});
        });
      }
    });
  }

  fetchExchangeRatesChanges(): void {
    const currencies = ['USD', 'EUR', 'GBP', 'CHF', 'JPY', 'CAD', 'AUD', 'CZK', 'DKK', 'NOK', 'SEK'];

    const today = new Date();
    let daysAgo = today.getDay() === 0 ? 2 : 1; // If today is Sunday, set daysAgo to 2, otherwise 1
    let dateAgo = new Date(today);
    dateAgo.setDate(today.getDate() - daysAgo);

    const requests = currencies.map(currency =>
      forkJoin({
        todayRate: this.currencyService.getCurrencyDetails(currency).pipe(catchError(error => {
          console.error('Error fetching todayRate for ' + currency, error);
          return of(null);
        })),
        oneDayAgoRate: this.currencyService.getCurrencyHistory(currency, dateAgo.toISOString().split('T')[0], today.toISOString().split('T')[0]).pipe(catchError(error => {
          console.error('Error fetching oneDayAgoRate for ' + currency, error);
          return of(null);
        }))
      }).pipe(
        map(({todayRate, oneDayAgoRate}) => {
          const rate = todayRate.rates[0].mid;
          const oldRate = oneDayAgoRate.rates[0].mid;
          const change = rate - oldRate;
          const percentageChange = (change / oldRate) * 100;

          return {from: currency, to: 'PLN', rate, change, percentageChange};
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
