import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserItemPage } from './user-item.page';

const routes: Routes = [
  {
    path: '',
    component: UserItemPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserItemPageRoutingModule {}
