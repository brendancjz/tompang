import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewUsersLikeListingPage } from './view-users-like-listing.page';

const routes: Routes = [
  {
    path: '',
    component: ViewUsersLikeListingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewUsersLikeListingPageRoutingModule {}
