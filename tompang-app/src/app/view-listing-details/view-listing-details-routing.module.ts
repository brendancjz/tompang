import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewListingDetailsPage } from './view-listing-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewListingDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewListingDetailsPageRoutingModule {}
