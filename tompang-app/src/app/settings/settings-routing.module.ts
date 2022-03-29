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
          path: '',
          redirectTo: '/settings',
          pathMatch: 'full'
        }
      ]
  },
  {
    path: 'edit-profile',
    loadChildren: () => import('./edit-profile/edit-profile.module').then( m => m.EditProfilePageModule)
  },
  {
    path: 'change-password',
    loadChildren: () => import('./change-password/change-password.module').then( m => m.ChangePasswordPageModule)
  },
  {
    path: 'manage-credit-cards',
    loadChildren: () => import('./manage-credit-cards/manage-credit-cards.module').then( m => m.ManageCreditCardsPageModule)
  },
  {
    path: 'change-profile-pic',
    loadChildren: () => import('./change-profile-pic/change-profile-pic.module').then( m => m.ChangeProfilePicPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SettingsPageRoutingModule {}
