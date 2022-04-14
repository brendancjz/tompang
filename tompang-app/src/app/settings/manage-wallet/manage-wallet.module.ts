import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ManageWalletPageRoutingModule } from './manage-wallet-routing.module';

import { ManageWalletPage } from './manage-wallet.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ManageWalletPageRoutingModule
  ],
  declarations: [ManageWalletPage]
})
export class ManageWalletPageModule {}
