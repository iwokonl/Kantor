import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CurrencyService } from '../currency.service';
import { CurrencyFlagsService } from '../currency-flags.service';

interface CurrencyFlags {
  [key: string]: string;
}

@Component({
  selector: 'app-currency-detail',
  templateUrl: './currency-detail.component.html',
  styleUrls: ['./currency-detail.component.scss']
})
export class CurrencyDetailComponent implements OnInit {
  code: string = '';
  currencyDetails: any;
  currencyFlags: { [key: string]: string } = {}; // Add this line

  constructor(private route: ActivatedRoute, private currencyService: CurrencyService, private currencyFlagsService: CurrencyFlagsService) {
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.code = params.get('code') ?? '';
      this.currencyService.getCurrencyDetails(this.code).subscribe(details => {
        this.currencyDetails = details;
      });
    });
  }
}
