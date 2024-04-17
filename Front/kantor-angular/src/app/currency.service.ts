// currency.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = 'http://api.nbp.pl/api/exchangerates/rates/A'; // Adres URL API NBP

  constructor(private http: HttpClient) { }

  getCurrencyDetails(code: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${code}/?format=json`);
  }

  getCurrencyHistory(code: string, startDate: string, endDate: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${code}/${startDate}/${endDate}/?format=json`).pipe(
      catchError(error => {
        console.error('Error fetching currency history', error);
        return throwError(error);
      })
    );
  }
}
