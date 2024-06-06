import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CurrencyAccountComponent} from './currency-account.component';

describe('CurrencyAccountComponent', () => {
  let component: CurrencyAccountComponent;
  let fixture: ComponentFixture<CurrencyAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CurrencyAccountComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CurrencyAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
