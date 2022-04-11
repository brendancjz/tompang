import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConfirmTransactionPage } from './confirm-transaction.page';

const routes: Routes = [
  {
    path: '',
    component: ConfirmTransactionPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConfirmTransactionPageRoutingModule {}
