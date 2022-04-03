import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DisputesPageRoutingModule } from './disputes-routing.module';

import { DisputesPage } from './disputes.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DisputesPageRoutingModule
  ],
  declarations: [DisputesPage]
})
export class DisputesPageModule {}
