// currency-chart.component.ts
// currency-chart.component.ts
import {Component, OnInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Chart} from 'chart.js';

@Component({
  selector: 'app-currency-chart',
  templateUrl: './currency-chart.component.html',
  styleUrls: ['./currency-chart.component.scss']
})
export class CurrencyChartComponent implements OnInit, OnChanges {
  @Input() currencyHistory: any;
  chart: any;

  constructor() {
  }

  ngOnInit() {
    this.createChart();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['currencyHistory'] && !changes['currencyHistory'].firstChange) {
      this.updateChart();
    }
  }

  createChart() {
    this.chart = new Chart('canvas', {
      type: 'line',
      data: {
        labels: this.currencyHistory.rates.map((rate: any) => rate.effectiveDate),
        datasets: [{
          data: this.currencyHistory.rates.map((rate: any) => rate.mid),
          borderColor: '#3cba9f',
          fill: false
        }]
      },
      options: {
        plugins: {
          legend: {
            display: false
          },
        },
        scales: {
          x: {
            display: true
          },
          y: {
            display: true
          },
        }
      }
    });
  }

  updateChart() {
    this.chart.data.labels = this.currencyHistory.rates.map((rate: any) => rate.effectiveDate);
    this.chart.data.datasets[0].data = this.currencyHistory.rates.map((rate: any) => rate.mid);
    this.chart.update();
  }
}
