import { Component, EventEmitter, Input, Output } from '@angular/core';
import { WishListService } from '../wishList.service';
import { AuthenticationService } from '../authentication.service';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-wishlist-conversion-button',
  templateUrl: './wishlist-conversion-button.component.html',
  styleUrl: './wishlist-conversion-button.component.css'
})
export class WishlistConversionButtonComponent {
  @Input() id!: number;

  @Input() quantity!: number;
  
  @Output() onRemove = new EventEmitter<number>();
  @Output() quantityConverted = new EventEmitter<number>();

  constructor(private authenticationService: AuthenticationService, private wishListService: WishListService, private basketService: BasketService){}

  conversion(){
    const uId=this.authenticationService.getCurrentUser().id;
    this.wishListService.removeFromWishList(uId,this.id).subscribe({
      next: (response) => {
        console.log(response);
        this.onRemove.emit(this.id);
      }
    })
    this.basketService.addToBasket(uId,this.id,this.quantity).subscribe({
      next: (response) => {
        console.log(response);
        this.quantityConverted.emit(this.quantity);
      }
    })
  }
}
