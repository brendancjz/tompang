import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConversationItemPageRoutingModule } from './conversation-item-routing.module';

import { ConversationItemPage } from './conversation-item.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConversationItemPageRoutingModule
  ],
  declarations: [ConversationItemPage],
  exports: [ConversationItemPage]
})
export class ConversationItemPageModule {}
