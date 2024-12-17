import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNeedPopupComponent } from './add-need-popup.component';

describe('AddNeedPopupComponent', () => {
  let component: AddNeedPopupComponent;
  let fixture: ComponentFixture<AddNeedPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddNeedPopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNeedPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
