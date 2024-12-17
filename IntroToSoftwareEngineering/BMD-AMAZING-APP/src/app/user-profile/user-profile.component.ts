import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { NeedSharingService } from '../need-sharing.service';
import { User } from '../user'; // Ensure this path is correct
import { SubService } from '../sub.service';
import { Observable } from 'rxjs';
import { NeedDatePair, SubscriptionResponse } from '../subscription-response';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  user!: User;
  isModalVisible = false;
  subscription$!: Observable<SubscriptionResponse>;
  subscriptionList: NeedDatePair[] = [];

  nextMonthFirstDate!: string

  constructor(
    private authenticationService: AuthenticationService,
    private needSharingService: NeedSharingService,
    private subService: SubService
  ) {}

  ngOnInit(): void {
    this.user = this.authenticationService.getCurrentUser();
    this.nextMonthFirstDate = this.getFirstDateOfNextMonth();
    this.subscription$ = this.subService.getSubscription(this.user.id);
    this.subscription$.subscribe(response => {
      this.needSharingService.updateSubscription(response.subscriptions);
      this.subscriptionList = response.subscriptions;
    });
  }

  onRemove(id: number){
    this.subscriptionList = this.subscriptionList.filter(needDatePair => needDatePair.need.id != id);
    this.needSharingService.updateSubscription(this.subscriptionList);
  }

  getFirstDateOfNextMonth(): string {
    const today = new Date();
    const nextMonth = new Date(today.getFullYear(), today.getMonth() + 1, 1);
    return nextMonth.toDateString();
  }

  // Modal handling methods
  showModal(): void {
    this.isModalVisible = true;
  }

  closeModal(): void {
    this.isModalVisible = false;
  }

  confirmDelete(): void {
    alert('Account deleted.'); // Replace with actual deletion logic
    this.closeModal();
  }

}
