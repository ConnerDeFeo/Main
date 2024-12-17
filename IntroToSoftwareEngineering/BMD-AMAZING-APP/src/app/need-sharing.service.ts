import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Need } from './need';
import { NeedDatePair } from './subscription-response';

@Injectable({
  providedIn: 'root'
})
export class NeedSharingService {
  private needsSubject = new BehaviorSubject<Need[]>([]);
  needs$ = this.needsSubject.asObservable();

  private needsBasket = new BehaviorSubject<Need[]>([]);
  needsBasket$ = this.needsBasket.asObservable();

  private subscriptionList = new BehaviorSubject<NeedDatePair[]>([]);
  subscriptionList$ = this.needsBasket.asObservable();
  
  private wishList = new BehaviorSubject<Need[]>([]);
  wishList$ = this.wishList.asObservable();

  updateNeeds(needs: Need[]){
    this.needsSubject.next(needs);
  }

  updateBasket(needs: Need[]){
    this.needsBasket.next(needs);
  }

  updateSubscription(needDatePairs: NeedDatePair[]){
    this.subscriptionList.next(needDatePairs);
  }
  
  updateWishList(needs: Need[]){
    this.wishList.next(needs);
  }
}
