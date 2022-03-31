import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { FooterPage } from './footer.page';

const routes: Routes = [
  {
    path: '',
    component: FooterPage,
    children:
      [
        {
          path: 'back',
          children:
            [
              {
                path: 'index',
                loadChildren: () => import('../index/index.module').then( m => m.IndexPageModule)
              }
            ]
        },
        {
          path: 'create',
          children:
            [
              {
                path: 'index',
                loadChildren: () => import('../index/index.module').then( m => m.IndexPageModule)
              }
            ]
        },
        {
          path: 'me',
          children:
            [
              {
                path: 'index',
                loadChildren: () => import('../index/index.module').then( m => m.IndexPageModule)
              }
            ]
        },
        {
          path: '',
          redirectTo: '/demo02/tab1',
          pathMatch: 'full'
        }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FooterPageRoutingModule {}
