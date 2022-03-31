import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BuyingPage } from './buying.page';

const routes: Routes = [
  {
    path: '',
    component: BuyingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BuyingPageRoutingModule {}
