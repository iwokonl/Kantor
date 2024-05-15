import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CurrencyService } from '../currency.service';
import { CurrencyFlagsService } from '../currency-flags.service';
import { Title } from '@angular/platform-browser';
import { Chart } from 'chart.js/auto';
import 'chartjs-adapter-date-fns';
import {forkJoin, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";


interface CurrencyFlags {
  [key: string]: string;
}

@Component({
  selector: 'app-currency-detail',
  templateUrl: './currency-detail.component.html',
  styleUrls: ['./currency-detail.component.scss']
})
export class CurrencyDetailComponent implements OnInit, OnDestroy {
  code: string = '';
  currencyDetails: any;
  currencyFlags: { [key: string]: string } = {};

  chart: Chart | undefined
  startDate: string = '';
  endDate: string = '';

  constructor(private route: ActivatedRoute, private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService, private titleService: Title) {
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();
  }
  changeDateRange(days: number) {
    const endDate = new Date();
    const startDate = new Date();
    startDate.setDate(endDate.getDate() - days);

    this.startDate = startDate.toISOString().split('T')[0];
    this.endDate = endDate.toISOString().split('T')[0];

    this.updateChartData();
  }

  formatDate(date: string): string {
    const [year, month, day] = date.split('-');
    return `${day}.${month}.${year}`;
  }




  // capitalizeWords(str: string): string { //Wszystkie pierwsze litery w słowach będą wielkie
  //   return str.split(' ')
  //     .map(word => word.charAt(0).toUpperCase() + word.slice(1))
  //     .join(' ');
  // }

  capitalizeFirstWord(str: string): string { //Pierwsza litera w zdaniu będzie wielka
  return str.split(' ')
    .map((word, index) => index === 0 ? word.charAt(0).toUpperCase() + word.slice(1) : word)
    .join(' ');
}

  updateChartData(){
    const currencyDetails$ = this.currencyService.getCurrencyDetails(this.code).pipe(
      catchError(error => {
        console.error('Error in getCurrencyDetails:', error);
        return throwError(error);
      })
    );

    const currencyHistory$ = this.currencyService.getCurrencyHistory(this.code, this.startDate, this.endDate).pipe(
      catchError(error => {
        console.error('Error in getCurrencyHistory:', error);
        return throwError(error);
      })
    )


    forkJoin([currencyDetails$, currencyHistory$]).subscribe(([details, history]) => {
      this.currencyDetails = details;
      this.currencyDetails.currency = this.capitalizeFirstWord(this.currencyDetails.currency);

      if (history.rates && Array.isArray(history.rates)) {
        const labels = history.rates.map((rate: any) => rate.effectiveDate);
        const values = history.rates.map((rate: any) => rate.mid);

        this.createChart(labels, values);
      } else {
        console.error('history.rates is not an array:', history.rates);
      }
    });

  }
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.code = params.get('code') ?? '';
      console.log('Code:', this.code); // Log the code

      const endDate = new Date();
      const startDate = new Date();
      startDate.setDate(endDate.getDate() - 14);

      this.startDate = startDate.toISOString().split('T')[0];
      this.endDate = endDate.toISOString().split('T')[0];

      this.updateChartData();
    });
  }

  createChart(labels: string[], values: number[]) {
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new Chart('chart', {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: this.code,
          data: values,
          borderColor: '#3cba9f',
          fill: false,
          tension: 1,
          cubicInterpolationMode: 'monotone',
        }]
      },
      options: {
        plugins: {
          legend: {
            onClick: (e, legendItem, legend) => {}
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                var label = context.dataset.label || '';

                if (label) {
                  label += ': ';
                }
                if (context.parsed.y !== null) {
                  label += new Intl.NumberFormat('en-US', { style: 'decimal' }).format(context.parsed.y) + ' PLN';
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
                day: 'dd.MM.yyyy'
              },
              parser: 'yyyy-MM-dd',
              tooltipFormat: 'dd.MM.yyyy',
            },
            ticks: {
              color: '#DBDBDB', // this will set the color of the labels on the x-axis
              callback: function(value, index, values) {
                // Format the date string in the Polish locale
                return new Intl.DateTimeFormat('pl-PL').format(new Date(value));
              }
            },
          },
          y: {
            type: 'linear',
            position: 'left',
            ticks: {
              color: '#DBDBDB' // this will set the color of the labels on the y-axis
            },
          }
        }
      }
    });



}

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }
}
