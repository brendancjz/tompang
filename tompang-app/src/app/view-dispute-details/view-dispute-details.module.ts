import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewDisputeDetailsPageRoutingModule } from './view-dispute-details-routing.module';

import { ViewDisputeDetailsPage } from './view-dispute-details.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewDisputeDetailsPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ViewDisputeDetailsPage]
})
export class ViewDisputeDetailsPageModule {}
