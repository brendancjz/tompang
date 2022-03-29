import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ManageCreditCardsPageRoutingModule } from './manage-credit-cards-routing.module';

import { ManageCreditCardsPage } from './manage-credit-cards.page';
import { HeaderPageModule } from 'src/app/header/header.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ManageCreditCardsPageRoutingModule,
    HeaderPageModule
  ],
  declarations: [ManageCreditCardsPage]
})
export class ManageCreditCardsPageModule {}
