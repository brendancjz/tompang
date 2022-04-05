import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ForSellersPageRoutingModule } from './for-sellers-routing.module';

import { ForSellersPage } from './for-sellers.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ForSellersPageRoutingModule
  ],
  declarations: [ForSellersPage]
})
export class ForSellersPageModule {}
