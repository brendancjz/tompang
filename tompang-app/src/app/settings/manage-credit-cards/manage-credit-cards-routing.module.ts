import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ManageCreditCardsPage } from './manage-credit-cards.page';

const routes: Routes = [
  {
    path: '',
    component: ManageCreditCardsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageCreditCardsPageRoutingModule {}
