import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNeedPopupComponent } from './edit-need-popup.component';

describe('EditNeedPopupComponent', () => {
  let component: EditNeedPopupComponent;
  let fixture: ComponentFixture<EditNeedPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditNeedPopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditNeedPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
