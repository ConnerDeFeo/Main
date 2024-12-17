import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNeedPopupComponent } from './create-need-popup.component';

describe('CreateNeedPopupComponent', () => {
  let component: CreateNeedPopupComponent;
  let fixture: ComponentFixture<CreateNeedPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateNeedPopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateNeedPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
