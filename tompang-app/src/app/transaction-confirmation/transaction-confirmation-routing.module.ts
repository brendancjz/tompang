import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TransactionConfirmationPage } from './transaction-confirmation.page';

const routes: Routes = [
  {
    path: '',
    component: TransactionConfirmationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TransactionConfirmationPageRoutingModule {}
