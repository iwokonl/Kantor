import { Component } from '@angular/core';

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
