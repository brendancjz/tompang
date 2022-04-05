import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BlogCardPage } from './blog-card.page';

const routes: Routes = [
  {
    path: '',
    component: BlogCardPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BlogCardPageRoutingModule {}
