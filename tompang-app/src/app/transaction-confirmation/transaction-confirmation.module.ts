import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TransactionConfirmationPageRoutingModule } from './transaction-confirmation-routing.module';

import { TransactionConfirmationPage } from './transaction-confirmation.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TransactionConfirmationPageRoutingModule,
  ],
  declarations: [TransactionConfirmationPage],
})
export class TransactionConfirmationPageModule {}
