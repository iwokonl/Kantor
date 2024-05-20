import {Component, Output, EventEmitter, Input} from '@angular/core';
import { polyfillCountryFlagEmojis } from "country-flag-emoji-polyfill";
import { CurrencyFlagsService } from '../currency-flags.service';

interface CurrencyFlags {
  [key: string]: string;
}

interface Result {
  code: keyof CurrencyFlags;
  name: string;
  message?: string;
}

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})

export class SearchResultsComponent {
  @Input() results: any[] = [];
  @Output() resultClick = new EventEmitter<void>();
  show: boolean = true;
  currencyFlags: { [key: string]: string } = {};

  constructor(private currencyFlagsService: CurrencyFlagsService) {
    polyfillCountryFlagEmojis();
    this.currencyFlags = this.currencyFlagsService.getCurrencyFlags();
  }

  onResultClick(): void {
    this.resultClick.emit();
  }

}
