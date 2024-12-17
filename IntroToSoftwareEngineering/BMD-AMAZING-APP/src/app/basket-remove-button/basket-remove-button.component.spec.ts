import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasketRemoveButtonComponent } from './basket-remove-button.component';

describe('BasketRemoveButtonComponent', () => {
  let component: BasketRemoveButtonComponent;
  let fixture: ComponentFixture<BasketRemoveButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BasketRemoveButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BasketRemoveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
