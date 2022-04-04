import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewConversationPageRoutingModule } from './view-conversation-routing.module';

import { ViewConversationPage } from './view-conversation.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewConversationPageRoutingModule,
    HeaderPageModule,
    FooterPageModule
  ],
  declarations: [ViewConversationPage]
})
export class ViewConversationPageModule {}
