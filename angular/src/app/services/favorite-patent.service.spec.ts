import { TestBed } from '@angular/core/testing';

import { FavoritePatentService } from './favorite-patent.service';

describe('FavoritePatentService', () => {
  let service: FavoritePatentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavoritePatentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
