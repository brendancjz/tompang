import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateListingPageRoutingModule } from './create-listing-routing.module';

import { CreateListingPage } from './create-listing.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateListingPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [CreateListingPage]
})
export class CreateListingPageModule {}
