// currency.service.ts
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {forkJoin, Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {AxiosService} from './axios.service';
import {from} from 'rxjs';
import {switchMap} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = 'http://api.nbp.pl/api/exchangerates/rates/A'; // Adres URL API NBP

  constructor(private http: HttpClient, private axiosService: AxiosService) {
  }

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
        map(({fromRate, toRate}) => {
          const rateFrom = fromRate.rates[0].mid;
          const rateTo = toRate.rates[0].mid;
          return rateFrom / rateTo;
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
        map(({fromRate, toRate}) => {
          const rateFrom = fromRate.rates[0].mid;
          const rateTo = toRate.rates[0].mid;
          return rateFrom / rateTo;
        }),
        catchError(error => {
          console.error('Error calculating exchange rate', error);
          return throwError(error);
        })
      );
    }
  }


  createCurrencyAccount(currencyId: string) {
    const url = 'http://localhost:8222/api/v1/currencyAccounts/createCurrencyAccount';
    const body = {id: currencyId};
    const headers = {
      'Authorization': 'Bearer ' + this.axiosService.getAuthTocken() // Add Authorization header
    };
    return this.http.post(url, body, {headers}); // Include headers in the request
  }

  getCurrencyId(code: string) {
    return this.axiosService.request('POST', '/api/v1/currencies/search', {name: code})
      .then(response => {
        // Assuming the response.data is the array of search results
        return response.data[0]?.id; // Get the id of the first element in the array
      })
      .catch(error => {
        console.error('Error fetching currency ID:', error);
        throw error;
      });
  }

  getPriceDifferenceOverTime(code: string, todayDate: string, daysAgo: string): Observable<{
    priceDifference: number,
    percentageDifference: number
  }> {
    return forkJoin({
      todayRate: this.http.get<any>(`${this.apiUrl}/${code}/${todayDate}/?format=json`),
      daysAgoRate: this.http.get<any>(`${this.apiUrl}/${code}/${daysAgo}/?format=json`)
    }).pipe(
      map(({todayRate, daysAgoRate}) => {
        const rateToday = todayRate.rates[0].mid;
        const rateDaysAgo = daysAgoRate.rates[0].mid;
        const priceDifference = rateToday - rateDaysAgo;
        const percentageDifference = (priceDifference / rateDaysAgo) * 100;

        return {priceDifference, percentageDifference};
      }),
      catchError(error => {
        console.error('Error calculating price difference over time', error);
        return throwError(error);
      })
    );
  }
}
