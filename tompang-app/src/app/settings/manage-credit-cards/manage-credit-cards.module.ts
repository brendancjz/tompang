import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ManageCreditCardsPageRoutingModule } from './manage-credit-cards-routing.module';

import { ManageCreditCardsPage } from './manage-credit-cards.page';
import { HeaderComponentModule } from 'src/app/header/header.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ManageCreditCardsPageRoutingModule,
    HeaderComponentModule
  ],
  declarations: [ManageCreditCardsPage]
})
export class ManageCreditCardsPageModule {}
