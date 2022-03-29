import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { InboxPage } from './inbox.page';

const routes: Routes = [
  {
    path: '',
    component: InboxPage
  },
  {
    path: 'notifications',
    loadChildren: () => import('./notifications/notifications.module').then( m => m.NotificationsPageModule)
  },
  {
    path: 'buying',
    loadChildren: () => import('./buying/buying.module').then( m => m.BuyingPageModule)
  },
  {
    path: 'selling',
    loadChildren: () => import('./selling/selling.module').then( m => m.SellingPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InboxPageRoutingModule {}
