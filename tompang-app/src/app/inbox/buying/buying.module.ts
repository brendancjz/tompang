import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BuyingPageRoutingModule } from './buying-routing.module';

import { BuyingPage } from './buying.page';
import { HeaderPageModule } from 'src/app/header/header.module';
import { FooterPageModule } from 'src/app/footer/footer.module';
import { ConversationItemPageModule } from 'src/app/components/conversation-item/conversation-item.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BuyingPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
    ConversationItemPageModule
  ],
  declarations: [BuyingPage]
})
export class BuyingPageModule {}
