import { TestBed } from '@angular/core/testing';

import { CurrencySearchService } from './currency-search.service';

describe('CurrencySearchService', () => {
  let service: CurrencySearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrencySearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
