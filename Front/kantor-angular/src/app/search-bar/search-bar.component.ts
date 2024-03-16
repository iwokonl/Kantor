import { Component } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';

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
        width: '1250px', // Zachowaj szerokość,
        height: '45px'
      })),
      transition('collapsed <=> expanded', animate('300ms ease-out')),
    ])
  ]
})
export class SearchBarComponent {
  state = 'collapsed';
  searchText: string = '';

  toggleSearchBar(): void {
    this.state = this.state === 'collapsed' ? 'expanded' : 'collapsed';
  }

  onInput(event: Event): void {
    this.searchText = (event.target as HTMLInputElement).value;
  }

  onBlur(): void {
    if (!this.searchText) {
      this.state = 'collapsed';
    }
  }
}
