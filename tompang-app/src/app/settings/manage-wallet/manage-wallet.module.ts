import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ManageWalletPageRoutingModule } from './manage-wallet-routing.module';

import { ManageWalletPage } from './manage-wallet.page';
import { HeaderPageModule } from 'src/app/header/header.module';
import { FooterPageModule } from 'src/app/footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ManageWalletPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ManageWalletPage]
})
export class ManageWalletPageModule {}
