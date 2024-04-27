import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CurrencyService } from '../currency.service';
import { CurrencyFlagsService } from '../currency-flags.service';
import { Title } from '@angular/platform-browser';

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

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.code = params.get('code') ?? '';
      this.currencyService.getCurrencyDetails(this.code).subscribe(details => {
        this.currencyDetails = details;
        this.currencyDetails.currency = this.capitalizeFirstWord(this.currencyDetails.currency);
        this.titleService.setTitle(this.capitalizeFirstWord(this.currencyDetails.currency)+" - Kantor $€££");
      });
    });
  }

  ngOnDestroy() {
    this.titleService.setTitle('Kantor $€££ - Wielowalutowy kantor online.');
  }
}
