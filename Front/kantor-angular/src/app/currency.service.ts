// currency.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {forkJoin, Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import { AxiosService } from './axios.service';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = 'http://api.nbp.pl/api/exchangerates/rates/A'; // Adres URL API NBP

  constructor(private http: HttpClient, private axiosService: AxiosService) { }

  getCurrencyDetails(code: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${code}/?format=json`);
  }

  getCurrencyDetailsByID(id: string): Observable<any> {
    return from(this.axiosService.getCurrencyData(id)).pipe(
      switchMap(data => this.getCurrencyDetails(data.code))
    );
  }

  getCurrencyHistory(code: string, startDate: string, endDate: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${code}/${startDate}/${endDate}/?format=json`).pipe(
      catchError(error => {
        console.error('Error fetching currency history', error);
        return throwError(error);
      })
    );
  }


  // getExchangeRate(fromCurrency: string, toCurrency: string): Observable<number> {
  //   return forkJoin({
  //     fromRate: this.getCurrencyDetails(fromCurrency),
  //     toRate: this.getCurrencyDetails(toCurrency)
  //   }).pipe(
  //     map(({ fromRate, toRate }) => {
  //       const rateFrom = fromRate.rates[0].mid;
  //       const rateTo = toRate.rates[0].mid;
  //       return  rateFrom / rateTo;
  //     }),
  //     catchError(error => {
  //       console.error('Error calculating exchange rate', error);
  //       return throwError(error);
  //     })
  //   );
  // }

  getExchangeRate(fromCurrency: string, toCurrency: string, startDate?: string, endDate?: string): Observable<number> {
    if (startDate && endDate) {
      // If start and end dates are provided, fetch the historical exchange rates
      return forkJoin({
        fromRate: this.getCurrencyHistory(fromCurrency, startDate, endDate),
        toRate: this.getCurrencyHistory(toCurrency, startDate, endDate)
      }).pipe(
        map(({ fromRate, toRate }) => {
          const rateFrom = fromRate.rates[0].mid;
          const rateTo = toRate.rates[0].mid;
          return  rateFrom / rateTo;
        }),
        catchError(error => {
          console.error('Error calculating exchange rate', error);
          return throwError(error);
        })
      );
    } else {
      // Otherwise, fetch the current exchange rates
      return forkJoin({
        fromRate: this.getCurrencyDetails(fromCurrency),
        toRate: this.getCurrencyDetails(toCurrency)
      }).pipe(
        map(({ fromRate, toRate }) => {
          const rateFrom = fromRate.rates[0].mid;
          const rateTo = toRate.rates[0].mid;
          return  rateFrom / rateTo;
        }),
        catchError(error => {
          console.error('Error calculating exchange rate', error);
          return throwError(error);
        })
      );
    }
  }
}
