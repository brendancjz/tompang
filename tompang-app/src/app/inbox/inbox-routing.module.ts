import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { InboxPage } from './inbox.page';

const routes: Routes = [
  {
    path: '',
    component: InboxPage,
    children:
      [
        {
          path: 'notifications',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./notifications/notifications.module').then( m => m.NotificationsPageModule)
              }
            ]
        },
        {
          path: 'buying',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./buying/buying.module').then( m => m.BuyingPageModule)
              }
            ]
        },
        {
          path: 'selling',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./selling/selling.module').then( m => m.SellingPageModule)
              }
            ]
        },
        {
          path: '',
          redirectTo: '/inbox/notifications',
          pathMatch: 'full'
        }
      ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InboxPageRoutingModule {}
