import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-currency-account',
  templateUrl: './currency-account.component.html',
  styleUrls: ['./currency-account.component.scss']
})
export class CurrencyAccountComponent implements OnInit {
  accounts: any[] = [];

  constructor(private axiosService: AxiosService) { }

  ngOnInit(): void {
    this.axiosService.request('POST', '/ForeignCurrencyAccount/getCurrencyAccounts', { userId: 1 })
      .then(response => {
        this.accounts = response.data;
      })
      .catch(error => {
        console.error('Error fetching currency accounts:', error);
      });
  }
}
