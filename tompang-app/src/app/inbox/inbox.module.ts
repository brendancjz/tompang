import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { InboxPageRoutingModule } from './inbox-routing.module';

import { InboxPage } from './inbox.page';
import { HeaderPageModule } from '../header/header.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    InboxPageRoutingModule,
    HeaderPageModule
  ],
  declarations: [InboxPage]
})
export class InboxPageModule {}
