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


  // createChart() {
  //   const endDate = new Date();
  //   const startDate = new Date();
  //   startDate.setDate(endDate.getDate() - 14);
  //
  //   this.currencyService.getCurrencyHistory(this.code, startDate.toISOString().split('T')[0], endDate.toISOString().split('T')[0])
  //     .subscribe(data => {
  //       const labels = data.Rates.map((rate: any) => rate.Rate.EffectiveDate);
  //       const values = data.Rates.map((rate: any) => rate.Rate.Mid);
  //
  //       console.log("Obecna data: "+endDate.toISOString().split('T')[0])
  //       console.log(labels);
  //       console.log(values);
  //
  //
  //       if (this.chart) {
  //         this.chart.destroy();
  //       }
  //
  //       this.chart = new Chart('chart', {
  //         type: 'line',
  //         data: {
  //           labels: labels,
  //           datasets: [{
  //             label: this.code,
  //             data: values,
  //             borderColor: '#3cba9f',
  //             fill: false
  //           }]
  //         },
  //         options: {
  //           scales: {
  //             x: {
  //               display: true,
  //               type: 'time',
  //               time: {
  //                 unit: 'day'
  //               }
  //             },
  //             y: {
  //               display: true,
  //               type: 'linear'
  //             }
  //           }
  //         }
  //       });
  //     });
  // }


  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.code = params.get('code') ?? '';
      console.log('Code:', this.code); // Log the code

      const endDate = new Date();
      const startDate = new Date();
      startDate.setDate(endDate.getDate() - 14);

      this.startDate = startDate.toISOString().split('T')[0];
      this.endDate = endDate.toISOString().split('T')[0];

      const currencyDetails$ = this.currencyService.getCurrencyDetails(this.code).pipe(
        catchError(error => {
          console.error('Error in getCurrencyDetails:', error);
          return throwError(error);
        })
      );

      const currencyHistory$ = this.currencyService.getCurrencyHistory(this.code, startDate.toISOString().split('T')[0], endDate.toISOString().split('T')[0]).pipe(
        catchError(error => {
          console.error('Error in getCurrencyHistory:', error);
          return throwError(error);
        })
      );


      forkJoin([currencyDetails$, currencyHistory$]).subscribe(([details, history]) => {
        console.log('Subscribed to forkJoin'); // Log subscription to forkJoin
        console.log('History:', history); // Log the history object
        this.currencyDetails = details;
        this.currencyDetails.currency = this.capitalizeFirstWord(this.currencyDetails.currency);
        this.titleService.setTitle(this.capitalizeFirstWord(this.currencyDetails.currency)+" - Kantor $€££");

        if (history.rates && Array.isArray(history.rates)) { // Change 'Rates' to 'rates'
          const labels = history.rates.map((rate: any) => rate.effectiveDate); // Change 'Rates' to 'rates'
          const values = history.rates.map((rate: any) => rate.mid); // Change 'Rates' to 'rates'

          console.log('Labels:', labels); // Log labels
          console.log('Values:', values); // Log values

          this.createChart(labels, values);
        } else {
          console.error('history.rates is not an array:', history.rates); // Change 'Rates' to 'rates'
        }
      });


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
          fill: false
        }]
      },
      options: {
        scales: {
          x: {
            type: 'time',
            time: {
              unit: 'day'
            }
          },
          y: {
            type: 'linear',
            position: 'left'
          }
        }
      }
    });



}

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }
}
