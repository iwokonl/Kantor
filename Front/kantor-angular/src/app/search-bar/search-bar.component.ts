import { Component, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
  animations: [
    trigger('expandCollapse', [
      state('collapsed', style({
        transform: 'translateX(0%)', // Element jest całkowicie przesunięty na prawo
        width: '45px', // Zakładając, że chcesz utrzymać stałą szerokość
        height: '45px'
      })),
      state('expanded', style({
        transform: 'translateX(0%)', // Element wraca do swojej początkowej pozycji
        // width: '1200px', // Zachowaj szerokość,
        width: '45vw',
        height: '45px'
      })),
      transition('collapsed <=> expanded', animate('600ms ease-in-out')),
    ])
  ]
})

export class SearchBarComponent implements OnInit, OnDestroy {
  state = 'collapsed';
  searchText: string = '';
  searchInput = new Subject<string>();
  searchSubscription!: Subscription;

  searchResults: any[] = [];

  constructor(private axiosService: AxiosService) {}

// // //!TODO - wersja bez axios
//   ngOnInit(): void {
//     this.searchSubscription = this.searchInput.pipe(
//       debounceTime(500)
//     ).subscribe(searchText => {
//       this.searchChange.emit(searchText);
//     });
//   }

//!TODO - wersja z axios
  ngOnInit(): void {
    this.searchSubscription = this.searchInput.pipe(
      debounceTime(500)
    ).subscribe(searchText => {
      this.axiosService.request('POST', '/api/currency/search', { name: searchText })
        .then(response => {
          this.searchResults = response.data;
          this.searchChange.emit(response.data);
        })
        .catch(error => {
          console.error('Error searching currencies:', error);
        });
    });
  }


  ngOnDestroy(): void {
    this.searchSubscription.unsubscribe();
  }

  toggleSearchBar(): void {
    this.state = this.state === 'collapsed' ? 'expanded' : 'collapsed';
  }

  @Output() searchChange = new EventEmitter<string>();

  onInput(event: Event): void {
    this.searchText = (event.target as HTMLInputElement).value;
    this.searchInput.next(this.searchText);
  }

  onBlur(): void {
    if (!this.searchText) {
      this.state = 'collapsed';
    }
  }
}
