import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HistoryPage } from './history.page';

const routes: Routes = [
  {
    path: '',
    component: HistoryPage,
    children:
      [
        {
          path: 'my-purchases',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./my-purchases/my-purchases.module').then( m => m.MyPurchasesPageModule)
              }
            ]
        },
        {
          path: 'disputes',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./disputes/disputes.module').then( m => m.DisputesPageModule)
              }
            ]
        },
        {
          path: '',
          redirectTo: '/history/my-purchases',
          pathMatch: 'full'
        }
      ]
  },
  {
    path: 'disputes',
    loadChildren: () => import('./disputes/disputes.module').then( m => m.DisputesPageModule)
  },
  {
    path: 'my-purchases',
    loadChildren: () => import('./my-purchases/my-purchases.module').then( m => m.MyPurchasesPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HistoryPageRoutingModule {}
