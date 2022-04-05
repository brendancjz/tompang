import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewCreditCardDetailsPageRoutingModule } from './view-credit-card-details-routing.module';

import { ViewCreditCardDetailsPage } from './view-credit-card-details.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewCreditCardDetailsPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ViewCreditCardDetailsPage]
})
export class ViewCreditCardDetailsPageModule {}
