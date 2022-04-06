import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewTransactionDetailsPage } from './view-transaction-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewTransactionDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewTransactionDetailsPageRoutingModule {}
