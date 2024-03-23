import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  @Input() ngModel: string = '';
  @Input() expandClasses: string = 'search-bar--expanded md-whiteframe-z2';
  @Input() markedClasses: string = 'search-bar--marked';


  @ViewChild('navburger') navburger!: ElementRef;

  displayBurger(): void {
    if (this.navburger.nativeElement.style.display === "block") {
      this.navburger.nativeElement.style.display = "none";
    } else {
      this.navburger.nativeElement.style.display = "block";
    }


  }


  hideNavList(): void { //ukrywanie listy z burgera
    const navList = this.navburger.nativeElement.querySelector('.nav-burger');
    navList.style.display = 'none';
  }

  handleSearchChange(newSearchText: string): void {
    // Handle the new search text here.
    console.log(newSearchText);
  }
}


