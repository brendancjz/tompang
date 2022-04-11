import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EditListingPagePage } from './edit-listing-page.page';

const routes: Routes = [
  {
    path: '',
    component: EditListingPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EditListingPagePageRoutingModule {}
