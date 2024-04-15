import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {User} from "./models/user";



@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getUsernameFromBackend(): Observable<HttpResponse<User>> {

    return this.http.get<User>('/api/authorization/userinfo',{ observe: 'response'} );
  }
}
