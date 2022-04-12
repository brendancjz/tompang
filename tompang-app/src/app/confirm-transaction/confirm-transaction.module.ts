import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConfirmTransactionPageRoutingModule } from './confirm-transaction-routing.module';

import { ConfirmTransactionPage } from './confirm-transaction.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConfirmTransactionPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ConfirmTransactionPage]
})
export class ConfirmTransactionPageModule {}
