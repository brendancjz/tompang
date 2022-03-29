import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BuyingPageRoutingModule } from './buying-routing.module';

import { BuyingPage } from './buying.page';
import { HeaderPageModule } from 'src/app/header/header.module';
import { FooterPageModule } from 'src/app/footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BuyingPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [BuyingPage]
})
export class BuyingPageModule {}
