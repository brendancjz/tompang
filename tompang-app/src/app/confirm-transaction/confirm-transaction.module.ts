import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConfirmTransactionPageRoutingModule } from './confirm-transaction-routing.module';

import { ConfirmTransactionPage } from './confirm-transaction.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConfirmTransactionPageRoutingModule
  ],
  declarations: [ConfirmTransactionPage]
})
export class ConfirmTransactionPageModule {}
