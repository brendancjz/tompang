import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewFollowersPageRoutingModule } from './view-followers-routing.module';

import { ViewFollowersPage } from './view-followers.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { UserItemPageModule } from '../components/user-item/user-item.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewFollowersPageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
    Ng2SearchPipeModule,
    UserItemPageModule
  ],
  declarations: [ViewFollowersPage]
})
export class ViewFollowersPageModule {}
