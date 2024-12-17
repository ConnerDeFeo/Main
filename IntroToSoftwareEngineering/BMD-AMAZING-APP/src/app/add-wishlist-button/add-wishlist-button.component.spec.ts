import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWishlistButtonComponent } from './add-wishlist-button.component';

describe('AddWishlistButtonComponent', () => {
  let component: AddWishlistButtonComponent;
  let fixture: ComponentFixture<AddWishlistButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddWishlistButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddWishlistButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
