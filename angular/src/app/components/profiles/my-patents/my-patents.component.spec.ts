import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MyPatentsComponent } from './my-patents.component';

describe('MyPatentsComponent', () => {
  let component: MyPatentsComponent;
  let fixture: ComponentFixture<MyPatentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyPatentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPatentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
