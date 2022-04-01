import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LikedListingsPageRoutingModule } from './liked-listings-routing.module';

import { LikedListingsPage } from './liked-listings.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LikedListingsPageRoutingModule
  ],
  declarations: [LikedListingsPage]
})
export class LikedListingsPageModule {}
