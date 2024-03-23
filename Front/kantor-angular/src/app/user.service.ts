import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsernameFromBackend(): Observable<any> {

    // console.log(this.http.get<any>('/api/authorization/userinfo').pipe(map(data => data.username)));
    return this.http.get<any>('/api/authorization/userinfo').pipe(map(data => data.username));
  }
}
