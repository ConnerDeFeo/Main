import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EditNeedPopupComponent } from '../edit-need-popup/edit-need-popup.component';
import { NeedService } from '../need.service';
import { Need } from '../need';

@Component({
  selector: 'app-need-button',
  templateUrl: './need-button.component.html',
  styleUrl: './need-button.component.css'
})

export class NeedButtonComponent {
  constructor(private dialog: MatDialog, private needService: NeedService){}
  
  @Input() need!: Need;
  @Input() role: 'admin' | 'user' | 'unassigned' = 'unassigned';
  
  @Output() onUpdate = new EventEmitter<Need>();
  
  openDialog(): void{
    const dialogRef = this.dialog.open(EditNeedPopupComponent, {
      width: '250px',
      data: [this.need, this.role]
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.submitNeed(result);
      }
    });
  }

  submitNeed(data: any){

    this.needService.updateNeed(data).subscribe({
      next: (response) => {
        console.log('Need submitted successfully', response);
        this.onUpdate.emit(data);
      },
      error: (error) => {
        console.error('Error submitting need', error);
      }
    });
  }

}
