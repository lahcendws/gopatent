import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentResultsPageComponent } from './patent-results-page.component';

describe('PatentResultsPageComponent', () => {
  let component: PatentResultsPageComponent;
  let fixture: ComponentFixture<PatentResultsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentResultsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatentResultsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
