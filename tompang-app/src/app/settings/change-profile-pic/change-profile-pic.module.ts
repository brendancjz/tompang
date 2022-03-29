import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChangeProfilePicPageRoutingModule } from './change-profile-pic-routing.module';

import { ChangeProfilePicPage } from './change-profile-pic.page';
import { HeaderPageModule } from 'src/app/header/header.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChangeProfilePicPageRoutingModule,
    HeaderPageModule
  ],
  declarations: [ChangeProfilePicPage]
})
export class ChangeProfilePicPageModule {}
