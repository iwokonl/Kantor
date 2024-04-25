import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import {Subject} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class AxiosService {
  private authStatusSubject = new Subject<boolean>();
  authStatus$ = this.authStatusSubject.asObservable();
  constructor(private router: Router) {
    axios.defaults.baseURL = 'http://localhost:8082';
    axios.defaults.headers.post['Content-Type'] = 'application/json';
  }

  getAuthTocken(): string | null {
    return window.localStorage.getItem('authToken');

  }
  setAuthTocken(token: string | null) : void {

    if (token !== null) {

        window.localStorage.setItem('authToken', token);


    }
    else {
      window.localStorage.removeItem('authToken');

    }
    this.authStatusSubject.next(!!token);
  }
  logout(): void {
    window.localStorage.removeItem('authToken');
    this.authStatusSubject.next(false);
    this.router.navigate(['/']);
  }
  checkAuthTocken(): void {
    if (this.getAuthTocken() === null || this.getAuthTocken() === undefined || this.getAuthTocken() === '' || this.getAuthTocken() === 'null') {
      window.localStorage.removeItem('authToken');
    }
  }
  request(method: string, url: string, data: any) {
    let headers = {};
    this.checkAuthTocken();
    if (this.getAuthTocken() !== null) {
      headers = {
        'Authorization': 'Bearer ' + this.getAuthTocken()
      };
      }
    return axios({
      method: method,
      url: url,
      data: data,
      headers: headers
    });
  }

  requestWithOutData(method: string, url: string) {
    let headers = {};
    this.checkAuthTocken();
    if (this.getAuthTocken() !== null) {
      headers = {
        'Authorization': 'Bearer ' + this.getAuthTocken()
      };
    }
    return axios({
      method: method,
      url: url,
      headers: headers
    });
  }
}
