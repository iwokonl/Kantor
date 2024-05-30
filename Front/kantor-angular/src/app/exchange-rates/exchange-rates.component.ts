import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CurrencyService } from '../currency.service';
import { CurrencyFlagsService } from '../currency-flags.service';
import { Chart } from 'chart.js/auto';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';

interface CurrencyFlags {
  [key: string]: string;
}

@Component({
  selector: 'app-exchange-rates',
  templateUrl: './exchange-rates.component.html',
  styleUrls: ['./exchange-rates.component.scss']
})
export class ExchangeRatesComponent implements OnInit, AfterViewInit {
  exchangeRatesChanges: { [days: number]: { from: string, to: string, rate: number, change: number, percentageChange: number }[] } = {};
  currencyFlags: CurrencyFlags = {};
  currencyNames: { [key: string]: string } = {};
  constructor(private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService) {
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();

  }

  ngOnInit(): void {
    this.fetchExchangeRatesChangesForPeriods();
    const currencies = ['USD', 'EUR', 'GBP', 'CHF', 'JPY', 'CAD', 'AUD', 'CZK', 'DKK', 'NOK', 'SEK']; // replace with your actual list of currencies
    currencies.forEach(currencyCode => {
      this.currencyService.getCurrencyDetails(currencyCode).subscribe(details => {
        this.currencyNames[currencyCode] = details.currency;
      });
    });
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.exchangeRatesChanges[30].forEach((currency, i) => {
        const startDate = new Date();
        startDate.setDate(startDate.getDate() - 30);
        const endDate = new Date();

        this.currencyService.getCurrencyHistory(currency.from, startDate.toISOString().split('T')[0], endDate.toISOString().split('T')[0])
          .subscribe(history => {
            if (history.rates && Array.isArray(history.rates)) {
              const labels = history.rates.map((rate: any) => rate.effectiveDate);
              const values = history.rates.map((rate: any) => rate.mid);

              this.createChart('chart' + i, labels, values);
            } else {
              console.error('history.rates is not an array:', history.rates);
            }
          });
      });
    }, 1000);
  }

  fetchExchangeRatesChanges(days: number): void {
    const currencies = ['USD', 'EUR', 'GBP', 'CHF', 'JPY', 'CAD', 'AUD', 'CZK', 'DKK', 'NOK', 'SEK'];

    const today = new Date();
    let dateAgo = new Date(today);
    dateAgo.setDate(today.getDate() - days);

    const requests = currencies.map(currency =>
      forkJoin({
        todayRate: this.currencyService.getCurrencyDetails(currency),
        daysAgoRate: this.currencyService.getCurrencyHistory(currency, dateAgo.toISOString().split('T')[0], today.toISOString().split('T')[0])
      }).pipe(
        map(({ todayRate, daysAgoRate }) => {
          const rate = todayRate.rates[0].mid;
          const oldRate = daysAgoRate.rates[0].mid;
          const change = rate - oldRate;
          const percentageChange = (change / oldRate) * 100;

          return { from: currency, to: 'PLN', rate, change, percentageChange };
        })
      )
    );

    forkJoin(requests).subscribe(results => {
      // Store the results in a property for the specific period
      this.exchangeRatesChanges[days] = results;
    });
  }

  fetchExchangeRatesChangesForPeriods(): void {
    this.fetchExchangeRatesChanges(7);
    this.fetchExchangeRatesChanges(30);
    this.fetchExchangeRatesChanges(90);
    this.fetchExchangeRatesChanges(365);
  }

  createChart(canvasId: string, labels: string[], values: number[]) {
    new Chart(canvasId, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          // label: 'Price Change Over 1 Month',
          data: values,
          borderColor: '#3cba9f',
          fill: false,
          tension: 1,
          cubicInterpolationMode: 'monotone',
        }]
      },
      options: {
        responsive: false,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
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
          },
        },
        scales: {
          x: {
            type: 'time',
            time: {
              unit: 'day',
              displayFormats: {
                day: 'dd.MM.yyyy'
              },
              parser: 'yyyy-MM-dd',
              tooltipFormat: 'dd.MM.yyyy',
            },
            ticks: {
              display: false, // Ukrycie dat na osi X
              color: '#DBDBDB',
              callback: function (value, index, values) {
                return new Intl.DateTimeFormat('pl-PL').format(new Date(value));
              }
            },
            grid: {
              color: 'rgba(219, 219, 219, 0)'
            }
          },
          y: {
            type: 'linear',
            position: 'left',
            ticks: {
              display: false,
              color: '#DBDBDB'
            },
            grid: {
              color: 'rgba(219, 219, 219, 0)',
            }
          }
        }
      }
    });
  }
  getChangeClass(change: number): string {
    if (change > 0) {
      return 'positive';
    } else if (change < 0) {
      return 'negative';
    } else {
      return 'zero';
    }
  }
}
