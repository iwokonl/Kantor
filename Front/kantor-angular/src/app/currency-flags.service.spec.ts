import { TestBed } from '@angular/core/testing';

import { CurrencyFlagsService } from './currency-flags.service';

describe('CurrencyFlagsService', () => {
  let service: CurrencyFlagsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrencyFlagsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
