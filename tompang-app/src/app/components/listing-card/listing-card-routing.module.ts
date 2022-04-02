import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ListingCardPage } from './listing-card.page';

const routes: Routes = [
  {
    path: '',
    component: ListingCardPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ListingCardPageRoutingModule {}
