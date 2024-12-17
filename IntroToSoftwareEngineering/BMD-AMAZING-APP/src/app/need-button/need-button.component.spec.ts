import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedButtonComponent } from './need-button.component';

describe('NeedButtonComponent', () => {
  let component: NeedButtonComponent;
  let fixture: ComponentFixture<NeedButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NeedButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeedButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
