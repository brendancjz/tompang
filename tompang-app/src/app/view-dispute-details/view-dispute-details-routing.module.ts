import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewDisputeDetailsPage } from './view-dispute-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewDisputeDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewDisputeDetailsPageRoutingModule {}
