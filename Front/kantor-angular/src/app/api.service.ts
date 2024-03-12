import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = 'http://localhost:8080'; // Adres URL twojego backendu

  constructor(private http: HttpClient) { }

  getSomeData(): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/your-endpoint`);
  }
}
