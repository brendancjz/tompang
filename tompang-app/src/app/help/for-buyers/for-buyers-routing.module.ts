import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ForBuyersPage } from './for-buyers.page';

const routes: Routes = [
  {
    path: '',
    component: ForBuyersPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ForBuyersPageRoutingModule {}
