import {Component, ElementRef, Input} from '@angular/core';
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


}
