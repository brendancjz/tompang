import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewCreditCardDetailsPage } from './view-credit-card-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewCreditCardDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewCreditCardDetailsPageRoutingModule {}
