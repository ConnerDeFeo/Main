import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BasketService } from '../basket.service';
import { AddNeedPopupComponent } from '../add-need-popup/add-need-popup.component';
import { AuthenticationService } from '../authentication.service';
import { Need } from '../need';
import { SubService } from '../sub.service';

@Component({
  selector: 'app-add-button',
  templateUrl: './add-button.component.html',
  styleUrl: './add-button.component.css'
})
export class AddButtonComponent {
  constructor(private dialog: MatDialog, private basketService: BasketService, private subscriptionService: SubService, private authenticationService: AuthenticationService){}

  @Input() need!: Need;
  @Output() onAdd = new EventEmitter<Need>();

  openDialog(): void{
    const dialogRef = this.dialog.open(AddNeedPopupComponent, {
      width: '250px',
      data: {need: this.need, title: "Add Need to Basket"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.addNeed(result);
      }
    });
  }

  addNeed(data: any){
    this.basketService.addToBasket(this.authenticationService.getCurrentUser().id, this.need.id, data.quantity).subscribe({
      next: (response) => {
        console.log('Need submitted successfully', response);
        this.onAdd.emit(this.need);
      },
      error: (error) => {
        console.error('Error submitting need', error);
      }
    });
    if (data.isChecked) {
      this.subscriptionService.addUnprocessedSubscription(this.authenticationService.getCurrentUser().id, this.need.id, data.quantity).subscribe({
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
}
