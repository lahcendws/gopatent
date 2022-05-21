import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentBoxComponent } from './patent-box.component';

describe('PatentBoxComponent', () => {
  let component: PatentBoxComponent;
  let fixture: ComponentFixture<PatentBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentBoxComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatentBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
