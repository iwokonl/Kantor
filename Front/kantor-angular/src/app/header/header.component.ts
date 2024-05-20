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

  constructor(public axiosService: AxiosService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.axiosService.getAuthTocken() !== null;
    this.authStatusSub = this.axiosService.authStatus$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    });
  }

  ngOnDestroy(): void {
    if (this.authStatusSub) {
      this.authStatusSub.unsubscribe();
    }
  }
  handleSearchChange(newSearchText: string): void {
    console.log(newSearchText);
  }
}


