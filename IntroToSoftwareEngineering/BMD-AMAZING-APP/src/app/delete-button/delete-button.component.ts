import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrl: './delete-button.component.css'
})
export class DeleteButtonComponent {
  @Input() id!: number;
  
  @Output() onDelete = new EventEmitter<number>();

  constructor(private needService: NeedService){}
  
  delete(){
    this.needService.deleteNeed(this.id).subscribe({
      next: (response) => {
        console.log(response);
        this.onDelete.emit(this.id);
      }
    })
  }
}
