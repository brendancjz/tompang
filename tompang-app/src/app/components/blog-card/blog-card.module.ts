import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BlogCardPageRoutingModule } from './blog-card-routing.module';

import { BlogCardPage } from './blog-card.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BlogCardPageRoutingModule
  ],
  declarations: [BlogCardPage],
  exports: [BlogCardPage]
})
export class BlogCardPageModule {}
