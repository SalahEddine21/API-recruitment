import { TestBed } from '@angular/core/testing';

import { RhService } from './rh.service';

describe('RhService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RhService = TestBed.get(RhService);
    expect(service).toBeTruthy();
  });
});
