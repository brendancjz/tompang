import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewConversationPage } from './view-conversation.page';

const routes: Routes = [
  {
    path: '',
    component: ViewConversationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewConversationPageRoutingModule {}
