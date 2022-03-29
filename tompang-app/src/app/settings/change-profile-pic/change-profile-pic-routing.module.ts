import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChangeProfilePicPage } from './change-profile-pic.page';

const routes: Routes = [
  {
    path: '',
    component: ChangeProfilePicPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChangeProfilePicPageRoutingModule {}
