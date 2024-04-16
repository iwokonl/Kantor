import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";
import {AxiosService} from "../axios.service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit, OnDestroy {

  @Input() ngModel: string = '';
  @Input() expandClasses: string = 'search-bar--expanded md-whiteframe-z2';
  @Input() markedClasses: string = 'search-bar--marked';
  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;

  @ViewChild('navburger') navburger!: ElementRef;
  constructor(public axiosService: AxiosService) { }
  displayBurger(): void {
    if (this.navburger.nativeElement.style.display === "block") {
      this.navburger.nativeElement.style.display = "none";
    } else {
      this.navburger.nativeElement.style.display = "block";
    }


  }
  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    });
  }
  hideNavList(): void { //ukrywanie listy z burgera
    const navList = this.navburger.nativeElement.querySelector('.nav-burger');
    navList.style.display = 'none';
  }
  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
  handleSearchChange(newSearchText: string): void {
    // Handle the new search text here.
    console.log(newSearchText);
  }
}


