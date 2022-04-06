import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewTransactionDetailsPageRoutingModule } from './view-transaction-details-routing.module';

import { ViewTransactionDetailsPage } from './view-transaction-details.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewTransactionDetailsPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ViewTransactionDetailsPage]
})
export class ViewTransactionDetailsPageModule {}
