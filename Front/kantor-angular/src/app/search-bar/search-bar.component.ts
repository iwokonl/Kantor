import {Component, Output, EventEmitter, OnInit, OnDestroy, HostListener, ElementRef} from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})

export class SearchBarComponent implements OnInit, OnDestroy {
  searchText: string = '';
  searchInput = new Subject<string>();
  searchSubscription!: Subscription;

  searchResults: any[] = [];
  lastSearchResults: any[] = [];

  constructor(private axiosService: AxiosService, private eRef: ElementRef) {}
  onResultClick(): void {
    this.searchResults = [];
  }
  ngOnInit(): void {
    this.searchSubscription = this.searchInput.pipe(
      debounceTime(500)
    ).subscribe(searchText => {
      this.axiosService.request('POST', '/api/v1/currencies/search', { name: searchText })
        .then(response => {
          this.searchResults = response.data;
          this.searchChange.emit(response.data);
          this.lastSearchResults = this.searchResults; // Move this line here
        })
        .catch(error => {
          console.error('Error searching currencies:', error);
          this.searchResults = [{message: 'Nie znaleziono waluty'}];
        });
    });
  }

  ngOnDestroy(): void {
    this.searchSubscription.unsubscribe();
  }

  @Output() searchChange = new EventEmitter<string>();

  onInput(event: Event): void {
    this.searchText = (event.target as HTMLInputElement).value;
    this.searchInput.next(this.searchText);
    this.lastSearchResults = this.searchResults;
  }

  @HostListener('document:click', ['$event'])
  clickout(event: MouseEvent) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.searchResults = [];
    }
  }

  onSearchBarClick(): void {
    if (this.searchResults.length === 0) {
      this.searchResults = this.lastSearchResults;
    }
  }
}
