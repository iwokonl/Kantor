import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AxiosService} from "../axios.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar-left',
  templateUrl: './sidebar-left.component.html',
  styleUrls: ['./sidebar-left.component.scss']
})
export class SidebarLeftComponent implements OnInit, OnDestroy {

  isLoggedIn: boolean = false;
  private authStatusSub: Subscription | undefined;

  constructor(public axiosService: AxiosService, public router: Router) { }

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
}
