import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LikedListingsPage } from './liked-listings.page';

const routes: Routes = [
  {
    path: '',
    component: LikedListingsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LikedListingsPageRoutingModule {}
