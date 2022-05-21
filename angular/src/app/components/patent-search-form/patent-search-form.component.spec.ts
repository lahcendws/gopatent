import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentSearchFormComponent } from './patent-search-form.component';

describe('PatentSearchFormComponent', () => {
  let component: PatentSearchFormComponent;
  let fixture: ComponentFixture<PatentSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentSearchFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatentSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
