import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'index',
    pathMatch: 'full'
  },
  {
    path: 'index',
    loadChildren: () => import('./index/index.module').then( m => m.IndexPageModule)
  },
  {
    path: 'shop',
    loadChildren: () => import('./shop/shop.module').then( m => m.ShopPageModule)
  },
  {
    path: 'profile',
    loadChildren: () => import('./profile/profile.module').then( m => m.ProfilePageModule)
  },
  {
    path: 'settings',
    loadChildren: () => import('./settings/settings.module').then( m => m.SettingsPageModule)
  },
  {
    path: 'inbox',
    loadChildren: () => import('./inbox/inbox.module').then( m => m.InboxPageModule)
  },
  {
    path: 'history',
    loadChildren: () => import('./history/history.module').then( m => m.HistoryPageModule)
  },
  {
    path: 'header',
    loadChildren: () => import('./header/header.module').then( m => m.HeaderPageModule)
  },
  {
    path: 'footer',
    loadChildren: () => import('./footer/footer.module').then( m => m.FooterPageModule)
  },
  {
    path: 'liked-listings',
    loadChildren: () => import('./liked-listings/liked-listings.module').then( m => m.LikedListingsPageModule)
  },
  {
    path: 'create-listing',
    loadChildren: () => import('./create-listing/create-listing.module').then( m => m.CreateListingPageModule)
  },
  {
    path: 'edit-profile',
    loadChildren: () => import('./settings/edit-profile/edit-profile.module').then( m => m.EditProfilePageModule)
  },
  {
    path: 'change-profile-pic',
    loadChildren: () => import('./settings/change-profile-pic/change-profile-pic.module').then( m => m.ChangeProfilePicPageModule)
  },
  {
    path: 'change-password',
    loadChildren: () => import('./settings/change-password/change-password.module').then( m => m.ChangePasswordPageModule)
  },
  {
    path: 'manage-credit-cards',
    loadChildren: () => import('./settings/manage-credit-cards/manage-credit-cards.module').then( m => m.ManageCreditCardsPageModule)
  },{
    path: '**',
    redirectTo: 'index',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
