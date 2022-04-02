import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SellingPageRoutingModule } from './selling-routing.module';

import { SellingPage } from './selling.page';
import { HeaderPageModule } from 'src/app/header/header.module';
import { FooterPageModule } from 'src/app/footer/footer.module';
import { ConversationItemPageModule } from 'src/app/components/conversation-item/conversation-item.module';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SellingPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
    ConversationItemPageModule,
    Ng2SearchPipeModule
  ],
  declarations: [SellingPage]
})
export class SellingPageModule {}
