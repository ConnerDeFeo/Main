import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { SubService } from '../sub.service';

@Component({
  selector: 'app-sub-delete',
  templateUrl: './sub-delete.component.html',
  styleUrl: './sub-delete.component.css'
})
export class SubDeleteComponent {
  @Input() id!: number;
  
  @Output() onRemove = new EventEmitter<number>();

  constructor(private authenticationService: AuthenticationService, private subService: SubService){}
  
  remove(){
    this.subService.deleteSubscription(this.authenticationService.getCurrentUser().id, this.id).subscribe({
      next: (response) => {
        console.log(response);
        this.onRemove.emit(this.id);
      }
    })
  }
}
