import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentDetailsComponent } from './patent-details.component';

describe('PatentDetailsComponent', () => {
  let component: PatentDetailsComponent;
  let fixture: ComponentFixture<PatentDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
