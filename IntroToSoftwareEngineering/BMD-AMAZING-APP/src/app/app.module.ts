import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { UserBasketComponent } from './user-basket/user-basket.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { NeedsComponent } from './needs/needs.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { DeleteButtonComponent } from './delete-button/delete-button.component';
import { CreateButtonComponent } from './create-button/create-button.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { CreateNeedPopupComponent } from './create-need-popup/create-need-popup.component';
import { NeedButtonComponent } from './need-button/need-button.component';
import { EditNeedPopupComponent } from './edit-need-popup/edit-need-popup.component';
import { AddButtonComponent } from './add-button/add-button.component';
import { AddNeedPopupComponent } from './add-need-popup/add-need-popup.component';
import { BasketRemoveButtonComponent } from './basket-remove-button/basket-remove-button.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SubDeleteComponent } from './sub-delete/sub-delete.component';
import { SubsComponent } from './subs/subs.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { AddWishlistButtonComponent } from './add-wishlist-button/add-wishlist-button.component';
import { AddWishlistRemoveButtonComponent } from './add-wishlist-remove-button/add-wishlist-remove-button.component';
import { WishlistConversionButtonComponent } from './wishlist-conversion-button/wishlist-conversion-button.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    SigninComponent,
    SignupComponent,
    UserBasketComponent,
    CheckoutComponent,
    NeedsComponent,
    NavigationBarComponent,
    UserProfileComponent,
    DeleteButtonComponent,
    CreateButtonComponent,
    SearchBarComponent,
    CreateNeedPopupComponent,
    NeedButtonComponent,
    EditNeedPopupComponent,
    AddButtonComponent,
    AddNeedPopupComponent,
    BasketRemoveButtonComponent,
    SubDeleteComponent,
    SubsComponent,
    WishlistComponent,
    AddWishlistButtonComponent,
    AddWishlistRemoveButtonComponent,
    WishlistConversionButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCheckboxModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
