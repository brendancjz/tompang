import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ForSellersPage } from './for-sellers.page';

const routes: Routes = [
  {
    path: '',
    component: ForSellersPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ForSellersPageRoutingModule {}
