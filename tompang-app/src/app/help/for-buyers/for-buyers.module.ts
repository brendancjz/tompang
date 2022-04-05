import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ForBuyersPageRoutingModule } from './for-buyers-routing.module';

import { ForBuyersPage } from './for-buyers.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ForBuyersPageRoutingModule
  ],
  declarations: [ForBuyersPage]
})
export class ForBuyersPageModule {}
