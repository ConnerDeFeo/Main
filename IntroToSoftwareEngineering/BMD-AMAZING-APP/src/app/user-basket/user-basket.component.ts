import { Component } from '@angular/core';
import { Need } from '../need';
import { User } from '../user';
import { AuthenticationService } from '../authentication.service';
import { NeedSharingService } from '../need-sharing.service';
import { BasketService } from '../basket.service';
import { Observable } from 'rxjs';
import { BasketResponse } from '../basket-response';
import { SubService } from '../sub.service';

@Component({
  selector: 'app-user-basket',
  templateUrl: './user-basket.component.html',
  styleUrl: './user-basket.component.css'
})
export class UserBasketComponent {
  needsBasket$!: Observable<BasketResponse>;
  myBasket: Need[] = [];
  user!: User;
  
  constructor(private needSharingService: NeedSharingService, private authenticationService: AuthenticationService, private basketService: BasketService, private subService: SubService){}

  ngOnInit(): void{
    this.user = this.authenticationService.getCurrentUser();
    this.needsBasket$ = this.basketService.getBasket(this.user.id);
    this.needsBasket$.subscribe(response => {
      this.needSharingService.updateBasket(response.basket);
      this.myBasket = response.basket;
    });
  }

  onRemove(id: number){
    this.myBasket = this.myBasket.filter(need => need.id != id);
    this.needSharingService.updateBasket(this.myBasket);
  }
  
  checkout(){
    let checkedOut = this.basketService.checkout(this.user.id);
    checkedOut.subscribe(response => {
      this.myBasket = [];
      this.needSharingService.updateBasket(this.myBasket);
    })
    this.subService.addSubscriptions(this.authenticationService.getCurrentUser().id).subscribe()
  }
}
