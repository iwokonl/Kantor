// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'app-currency-detail',
//   templateUrl: './currency-detail.component.html',
//   styleUrl: './currency-detail.component.scss'
// })
// export class CurrencyDetailComponent {
//
// }



import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-currency-detail',
  template: `
    <p>
      Currency code: {{ code }}
    </p>
  `,
  styles: []
})
export class CurrencyDetailComponent implements OnInit {
  code: string = '';

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.code = this.route.snapshot.paramMap.get('code') ?? '';
  }
}
