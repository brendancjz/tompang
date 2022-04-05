import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HelpPage } from './help.page';

const routes: Routes = [
  {
    path: '',
    component: HelpPage,
    children:
      [
        {
          path: 'for-buyers',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./for-buyers/for-buyers.module').then( m => m.ForBuyersPageModule)
              }
            ]
        },
        {
          path: 'for-sellers',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./for-sellers/for-sellers.module').then( m => m.ForSellersPageModule)
              }
            ]
        },
        {
          path: '',
          redirectTo: '/help/for-buyers',
          pathMatch: 'full'
        }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HelpPageRoutingModule {}
