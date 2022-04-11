import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EditListingPagePageRoutingModule } from './edit-listing-page-routing.module';

import { EditListingPagePage } from './edit-listing-page.page';
import { HeaderPageModule } from '../header/header.module';
import { FooterPageModule } from '../footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EditListingPagePageRoutingModule,
    HeaderPageModule,
    FooterPageModule,
  ],
  declarations: [EditListingPagePage],
})
export class EditListingPagePageModule {}
