import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CurrencyService } from '../currency.service';

@Component({
  selector: 'app-currency-detail',
  templateUrl: './currency-detail.component.html',
  styleUrls: ['./currency-detail.component.scss']
})
export class CurrencyDetailComponent implements OnInit {
  code: string = '';
  currencyDetails: any;

  constructor(private route: ActivatedRoute, private currencyService: CurrencyService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.code = params.get('code') ?? '';
      this.currencyService.getCurrencyDetails(this.code).subscribe(details => {
        this.currencyDetails = details;
      });
    });
  }
}
