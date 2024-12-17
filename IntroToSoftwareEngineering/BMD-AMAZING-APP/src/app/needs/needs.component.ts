import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Need, needType } from '../need';
import { AuthenticationService } from '../authentication.service';
import { NeedSharingService } from '../need-sharing.service';
import { User } from '../user';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css'],
})
export class NeedsComponent implements OnInit {
  needs: Need[] = [];
  userRole: 'admin' | 'user' | 'unassigned' = 'unassigned';
  needsBasket: Need[] = [];
  user!: User;
  needType = needType;

  constructor(
    private needSharingService: NeedSharingService,
    private authenticationService: AuthenticationService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.userRole = this.authenticationService.getRole();
    this.user = this.authenticationService.getCurrentUser();
    

    this.needSharingService.needs$.subscribe(
      (needs) => {
        this.needs = needs;
      },
      (error) => console.error(error)
    );

    this.needSharingService.needsBasket$.subscribe(
      (needsBasket) => {
        this.needsBasket = needsBasket;
      },
      (error) => console.error(error)
    );
  }

  onDelete(id: number): void {
    this.needs = this.needs.filter(need => need.id !== id);
    this.needSharingService.updateNeeds(this.needs);
  }

  onCreate(need: Need): void {
    this.needs.push(need);
    this.needSharingService.updateNeeds(this.needs);
  }

  onEdit(need: Need): void {
    for (let i in this.needs) {
      if (this.needs[i].id === need.id) {
        this.needs[i] = need;
        break;
      }
    }
    this.needSharingService.updateNeeds(this.needs);
    this.cdRef.detectChanges();
  }

  onAdd(need: Need): void {
    this.needsBasket.push(need);
    this.needSharingService.updateBasket(this.needsBasket);
  }
}
