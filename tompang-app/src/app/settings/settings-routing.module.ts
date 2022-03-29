import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SettingsPage } from './settings.page';

const routes: Routes = [
  {
    path: '',
    component: SettingsPage,
    children:
      [
        {
          path: 'edit-profile',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./edit-profile/edit-profile.module').then( m => m.EditProfilePageModule)
              }
            ]
        },
        {
          path: 'change-profile-pic',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./change-profile-pic/change-profile-pic.module').then( m => m.ChangeProfilePicPageModule)
              }
            ]
        },
        {
          path: 'change-password',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./change-password/change-password.module').then( m => m.ChangePasswordPageModule)
              }
            ]
        },
        {
          path: 'manage-credit-cards',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./manage-credit-cards/manage-credit-cards.module').then( m => m.ManageCreditCardsPageModule)
              }
            ]
        },
        {
          path: 'tabs',
          children:
            [
              {
                path: '',
                loadChildren: () => import('./tabs/tabs.module').then( m => m.TabsPageModule)
              }
            ]
        },
        {
          path: '',
          redirectTo: '/settings/tabs',
          pathMatch: 'full'
        }
      ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SettingsPageRoutingModule {}
