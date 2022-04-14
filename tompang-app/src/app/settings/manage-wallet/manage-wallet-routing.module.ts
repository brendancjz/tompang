import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ManageWalletPage } from './manage-wallet.page';

const routes: Routes = [
  {
    path: '',
    component: ManageWalletPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageWalletPageRoutingModule {}
