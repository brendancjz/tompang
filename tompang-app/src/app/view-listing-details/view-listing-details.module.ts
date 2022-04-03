import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewListingDetailsPageRoutingModule } from './view-listing-details-routing.module';

import { ViewListingDetailsPage } from './view-listing-details.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewListingDetailsPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,

  ],
  declarations: [ViewListingDetailsPage]
})
export class ViewListingDetailsPageModule {}
