import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LikedListingsPageRoutingModule } from './liked-listings-routing.module';

import { LikedListingsPage } from './liked-listings.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';
import { ListingCardPageModule } from '../components/listing-card/listing-card.module';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LikedListingsPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
    ListingCardPageModule,
    Ng2SearchPipeModule
  ],
  declarations: [LikedListingsPage]
})
export class LikedListingsPageModule {}
