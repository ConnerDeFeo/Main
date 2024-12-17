import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WishlistConversionButtonComponent } from './wishlist-conversion-button.component';

describe('WishlistConversionButtonComponent', () => {
  let component: WishlistConversionButtonComponent;
  let fixture: ComponentFixture<WishlistConversionButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WishlistConversionButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WishlistConversionButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
