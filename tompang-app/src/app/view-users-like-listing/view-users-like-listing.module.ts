import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewUsersLikeListingPageRoutingModule } from './view-users-like-listing-routing.module';

import { ViewUsersLikeListingPage } from './view-users-like-listing.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { UserItemPageModule } from '../components/user-item/user-item.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewUsersLikeListingPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
    Ng2SearchPipeModule,
    UserItemPageModule
  ],
  declarations: [ViewUsersLikeListingPage]
})
export class ViewUsersLikeListingPageModule {}
