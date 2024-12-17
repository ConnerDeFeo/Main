import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWishlistRemoveButtonComponent } from './add-wishlist-remove-button.component';

describe('AddWishlistRemoveButtonComponent', () => {
  let component: AddWishlistRemoveButtonComponent;
  let fixture: ComponentFixture<AddWishlistRemoveButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddWishlistRemoveButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddWishlistRemoveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
