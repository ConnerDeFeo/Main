import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { WishListResponse } from '../wishlistResponse';
import { Need } from '../need';
import { User } from '../user';
import { NeedSharingService } from '../need-sharing.service';
import { AuthenticationService } from '../authentication.service';
import { WishListService } from '../wishList.service';
import { BasketService } from '../basket.service';
import { BasketResponse } from '../basket-response';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent {
  wishList$!: Observable<WishListResponse>;
  myWishlist: Need[]=[];
  user!: User;

  constructor(private needSharingService: NeedSharingService, private authenticationService: AuthenticationService,private wishListService: WishListService, private basketService: BasketService){}

  ngOnInit(): void{
    this.user=this.authenticationService.getCurrentUser();
    this.wishList$=this.wishListService.getWishlist(this.user.id);
    this.wishList$.subscribe(response =>{
      this.needSharingService.updateWishList(response.wishList);
      this.myWishlist=response.wishList;
      console.log(response);
    });
  }

  onRemove(id: number){
    this.myWishlist=this.myWishlist.filter(need=>need.id!=id);
    this.needSharingService.updateWishList(this.myWishlist);
  }

  quantityConverted(needId:number,quantity: number){
    this.basketService.addToBasket(this.user.id,needId,quantity);
  }
}
