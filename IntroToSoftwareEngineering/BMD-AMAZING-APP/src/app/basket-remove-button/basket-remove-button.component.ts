import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasketService } from '../basket.service';
import { AuthenticationService } from '../authentication.service';
import { SubService } from '../sub.service';

@Component({
  selector: 'app-basket-remove-button',
  templateUrl: './basket-remove-button.component.html',
  styleUrl: './basket-remove-button.component.css'
})
export class BasketRemoveButtonComponent {
  @Input() id!: number;
  
  @Output() onRemove = new EventEmitter<number>();

  constructor(private authenticationService: AuthenticationService, private basketService: BasketService, private subService: SubService){}
  
  remove(){
    this.basketService.removeFromBasket(this.authenticationService.getCurrentUser().id, this.id).subscribe({
      next: (response) => {
        console.log(response);
        this.onRemove.emit(this.id);
      }
    })
    this.subService.getUnprocessed(this.authenticationService.getCurrentUser().id, this.id).subscribe(
      (response) => {
        if (response.status === 200) {
          this.subService.deleteUnprocessedSubscription(this.authenticationService.getCurrentUser().id, this.id).subscribe()
          console.log('Subscription found and is unprocessed');
        } else {
          console.log('Subscription not found');
        }
      },
      (error) => {
        console.error('Error occurred:', error);
      }
    );
  }
}
