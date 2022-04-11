import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UserItemPageRoutingModule } from './user-item-routing.module';

import { UserItemPage } from './user-item.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UserItemPageRoutingModule
  ],
  declarations: [UserItemPage],
  exports: [UserItemPage]
})
export class UserItemPageModule {}
