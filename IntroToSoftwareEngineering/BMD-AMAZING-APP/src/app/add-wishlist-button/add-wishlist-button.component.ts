import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { WishListService } from '../wishList.service';
import { MatDialog } from '@angular/material/dialog';
import { Need } from '../need';
import { AddNeedPopupComponent } from '../add-need-popup/add-need-popup.component';

@Component({
  selector: 'app-add-wishlist-button',
  templateUrl: './add-wishlist-button.component.html',
  styleUrl: './add-wishlist-button.component.css'
})
export class AddWishlistButtonComponent {
  constructor(private dialog: MatDialog, private wishlistService: WishListService, private authenticationService: AuthenticationService){}

  @Input() need!: Need;
  @Output() onAdd = new EventEmitter<Need>();

  openDialog(): void{
    const dialogRef = this.dialog.open(AddNeedPopupComponent, {
      width: '250px',
      data: {need:this.need,title:"Add Need to Wishlist"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.addNeed(result);
      }
    });
  }

  addNeed(data: any){
    this.wishlistService.addToWishlist(this.authenticationService.getCurrentUser().id, this.need.id, data.quantity).subscribe({
      next: (response) => {
        console.log('Need submitted successfully', response);
        this.onAdd.emit(this.need);
      },
      error: (error) => {
        console.error('Error submitting need', error);
      }
    });
  }
}
