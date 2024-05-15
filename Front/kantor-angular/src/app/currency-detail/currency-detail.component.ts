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
          cubicInterpolationMode: 'monotone'
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
