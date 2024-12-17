import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserBasketComponent } from './user-basket/user-basket.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { NeedsComponent } from './needs/needs.component';
import { WishlistComponent } from './wishlist/wishlist.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'signin', component: SigninComponent},
  { path: 'signup', component: SignupComponent},
  { path: 'needs', component: NeedsComponent},
  { path: 'profile', component: UserProfileComponent},
  { path: 'basket', component: UserBasketComponent},
  { path: 'checkout', component: CheckoutComponent},
  { path: 'wishlist', component: WishlistComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
