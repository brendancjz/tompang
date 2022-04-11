import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewFollowersPage } from './view-followers.page';

const routes: Routes = [
  {
    path: '',
    component: ViewFollowersPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewFollowersPageRoutingModule {}
