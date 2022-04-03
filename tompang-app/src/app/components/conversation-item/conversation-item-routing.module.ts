import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConversationItemPage } from './conversation-item.page';

const routes: Routes = [
  {
    path: '',
    component: ConversationItemPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConversationItemPageRoutingModule {}
