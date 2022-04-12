import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'index',
    pathMatch: 'full',
  },
  {
    path: 'index',
    loadChildren: () =>
      import('./index/index.module').then((m) => m.IndexPageModule),
  },
  {
    path: 'shop',
    loadChildren: () =>
      import('./shop/shop.module').then((m) => m.ShopPageModule),
  },
  {
    path: 'profile/:userId',
    loadChildren: () =>
      import('./profile/profile.module').then((m) => m.ProfilePageModule),
  },
  {
    path: 'settings',
    loadChildren: () =>
      import('./settings/settings.module').then((m) => m.SettingsPageModule),
  },
  {
    path: 'inbox',
    loadChildren: () =>
      import('./inbox/inbox.module').then((m) => m.InboxPageModule),
  },
  {
    path: 'history',
    loadChildren: () =>
      import('./history/history.module').then((m) => m.HistoryPageModule),
  },
  {
    path: 'header',
    loadChildren: () =>
      import('./header/header.module').then((m) => m.HeaderPageModule),
  },
  {
    path: 'footer',
    loadChildren: () =>
      import('./footer/footer.module').then((m) => m.FooterPageModule),
  },
  {
    path: 'liked-listings',
    loadChildren: () =>
      import('./liked-listings/liked-listings.module').then(
        (m) => m.LikedListingsPageModule
      ),
  },
  {
    path: 'create-listing',
    loadChildren: () =>
      import('./create-listing/create-listing.module').then(
        (m) => m.CreateListingPageModule
      ),
  },
  {
    path: 'edit-profile',
    loadChildren: () =>
      import('./settings/edit-profile/edit-profile.module').then(
        (m) => m.EditProfilePageModule
      ),
  },
  {
    path: 'change-profile-pic',
    loadChildren: () =>
      import('./settings/change-profile-pic/change-profile-pic.module').then(
        (m) => m.ChangeProfilePicPageModule
      ),
  },
  {
    path: 'change-password',
    loadChildren: () =>
      import('./settings/change-password/change-password.module').then(
        (m) => m.ChangePasswordPageModule
      ),
  },
  {
    path: 'manage-credit-cards',
    loadChildren: () =>
      import('./settings/manage-credit-cards/manage-credit-cards.module').then(
        (m) => m.ManageCreditCardsPageModule
      ),
  },
  {
    path: 'view-listing-details/:listingId',
    loadChildren: () =>
      import('./view-listing-details/view-listing-details.module').then(
        (m) => m.ViewListingDetailsPageModule
      ),
  },
  {
    path: 'view-conversation/:convoId',
    loadChildren: () =>
      import('./view-conversation/view-conversation.module').then(
        (m) => m.ViewConversationPageModule
      ),
  },
  {
    path: 'create-transaction/:listingId',
    loadChildren: () =>
      import('./create-transaction/create-transaction.module').then(
        (m) => m.CreateTransactionPageModule
      ),
  },
  {
    path: 'view-credit-card-details/:ccId',
    loadChildren: () =>
      import('./view-credit-card-details/view-credit-card-details.module').then(
        (m) => m.ViewCreditCardDetailsPageModule
      ),
  },
  {
    path: 'view-transaction-details/:transactionId',
    loadChildren: () =>
      import('./view-transaction-details/view-transaction-details.module').then(
        (m) => m.ViewTransactionDetailsPageModule
      ),
  },
  {
    path: 'blog',
    loadChildren: () =>
      import('./blog/blog.module').then((m) => m.BlogPageModule),
  },
  {
    path: 'help',
    loadChildren: () =>
      import('./help/help.module').then((m) => m.HelpPageModule),
  },
  {
    path: 'view-dispute-details/:disputeId',
    loadChildren: () =>
      import('./view-dispute-details/view-dispute-details.module').then(
        (m) => m.ViewDisputeDetailsPageModule
      ),
  },
  {
    path: 'view-followers/:userId',
    loadChildren: () =>
      import('./view-followers/view-followers.module').then(
        (m) => m.ViewFollowersPageModule
      ),
  },
  {
    path: 'view-following/:userId',
    loadChildren: () =>
      import('./view-following/view-following.module').then(
        (m) => m.ViewFollowingPageModule
      ),
  },
  {
    path: 'confirm-transaction/:transactionId',
    loadChildren: () =>
      import('./confirm-transaction/confirm-transaction.module').then(
        (m) => m.ConfirmTransactionPageModule
      ),
  },

  {
    path: 'edit-listing-page/:listingId',
    loadChildren: () =>
      import('./edit-listing-page/edit-listing-page.module').then(
        (m) => m.EditListingPagePageModule
      ),
  },
  {
    path: 'view-users-like-listing/:listingId',
    loadChildren: () => import('./view-users-like-listing/view-users-like-listing.module').then( m => m.ViewUsersLikeListingPageModule)
  },
  {
    path: '**',
    redirectTo: 'index',
    pathMatch: 'full',
  },


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
