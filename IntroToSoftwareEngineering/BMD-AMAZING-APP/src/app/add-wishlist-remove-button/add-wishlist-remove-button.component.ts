import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { WishListService } from '../wishList.service';

@Component({
  selector: 'app-add-wishlist-remove-button',
  templateUrl: './add-wishlist-remove-button.component.html',
  styleUrl: './add-wishlist-remove-button.component.css'
})
export class AddWishlistRemoveButtonComponent {
  @Input() id!: number;
  
  @Output() onRemove = new EventEmitter<number>();

  constructor(private authenticationService: AuthenticationService, private wishListService: WishListService){}
  
  remove(){
    this.wishListService.removeFromWishList(this.authenticationService.getCurrentUser().id, this.id).subscribe({
      next: (response) => {
        console.log(response);
        this.onRemove.emit(this.id);
      }
    })
  }
}
