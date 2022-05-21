import { TestBed } from '@angular/core/testing';

import { TimerRedirectLoginService } from './timer-redirect-login.service';

describe('TimerRedirectLoginService', () => {
  let service: TimerRedirectLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimerRedirectLoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
