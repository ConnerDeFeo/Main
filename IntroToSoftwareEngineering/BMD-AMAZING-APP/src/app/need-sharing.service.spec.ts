import { TestBed } from '@angular/core/testing';

import { NeedSharingService } from './need-sharing.service';

describe('NeedSharingService', () => {
  let service: NeedSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NeedSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
