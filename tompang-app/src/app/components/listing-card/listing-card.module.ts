import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListingCardPageRoutingModule } from './listing-card-routing.module';

import { ListingCardPage } from './listing-card.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ListingCardPageRoutingModule
  ],
  declarations: [ListingCardPage],
  exports: [ListingCardPage]
})
export class ListingCardPageModule {}
