import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewFollowingPage } from './view-following.page';

const routes: Routes = [
  {
    path: '',
    component: ViewFollowingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewFollowingPageRoutingModule {}
