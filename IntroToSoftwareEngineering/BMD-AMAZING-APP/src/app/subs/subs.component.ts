import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NeedDatePair } from '../subscription-response';
import { Need } from '../need';
import { EditNeedPopupComponent } from '../edit-need-popup/edit-need-popup.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-subs',
  templateUrl: './subs.component.html',
  styleUrl: './subs.component.css'
})
export class SubsComponent {
  @Input() needDatePair!: NeedDatePair;

  need!: Need;
  date!: string;

  constructor(private dialog: MatDialog){};

  ngOnInit(): void {
    this.need = this.needDatePair.need;
    this.date = this.needDatePair.date;
  }

  openDialog(): void{
    this.dialog.open(EditNeedPopupComponent, {
      width: '250px',
      data: [this.need, 'user']
    });
  }
}
