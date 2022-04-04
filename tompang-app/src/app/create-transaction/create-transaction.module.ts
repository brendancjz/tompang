import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateTransactionPageRoutingModule } from './create-transaction-routing.module';

import { CreateTransactionPage } from './create-transaction.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateTransactionPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [CreateTransactionPage]
})
export class CreateTransactionPageModule {}
