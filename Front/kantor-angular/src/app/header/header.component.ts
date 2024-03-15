import {Component, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import {CurrencyDto} from "../currency-search.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  searchExpanded: boolean = false;

  expandSearch(): void {
    this.searchExpanded = true;
  }

  collapseSearch(event: any): void {
    if (!event.target.value.trim()) {
      this.searchExpanded = false;
    }
  }

  onInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    console.log(value);
  }
}

 @Injectable()
export class CurrencyService {
  constructor(private http: HttpClient) { }
  private apiUrl = 'http://localhost:4200'
  getCurrency(searchValue: string): Observable<CurrencyDto[]> {
    return this.http.get<CurrencyDto[]>( `http://localhost:4200/=${searchValue}`
    );
  }
 }
