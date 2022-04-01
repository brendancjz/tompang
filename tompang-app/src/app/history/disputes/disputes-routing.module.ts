import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DisputesPage } from './disputes.page';

const routes: Routes = [
  {
    path: '',
    component: DisputesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DisputesPageRoutingModule {}
