# Tompang
<div style="display: flex; align-items: center; justify-content: space-around; width: 40%; margin: 0 auto;">
  <img src="https://github.com/brendancjz/tompang/blob/main/Tompang/Tompang-war/web/resources/images/tompang_icon_logo_white.png" width="100">                        
  <img src="https://github.com/brendancjz/tompang/blob/main/Tompang/Tompang-war/web/resources/images/tompang_logo_white.png" width="200">  
</div>

## What is Tompang?

Tompang is a mobile application acting as a marketplace for Singaporean travelers. During their overseas trips, travelers can offer to buy back overseas products for interested buyers in Singapore. This project allows travelers to earn some extra income while they are on their trip. At the same time, it also offers Singaporeans the opportunity to either
   - purchase products from overseas without bearing expensive shipping costs or
   - purchase products that they would not have been able to purchase directly from Singapore.

## Useful Links

<a href="https://uvents.nus.edu.sg/event/20th-steps/module/IS3106/project/4" target="_blank">Featured on The 20th SoC Term Project Showcase</a>
<br />
<a href="https://youtu.be/9AliJYcd-K8">Tompang's Mobile App Demo Video</a>

## Mobile App Features
<div style="display: flex; align-items: center; justify-content: space-around;">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-Marketplace.jpg" style="width: 300px">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-Real-Time-Chat.jpg" style="width: 300px">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-FollowLike.jpg" style="width: 300px">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-FollowLike2.jpg" style="width: 300px">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-QRCode.jpg" style="width: 300px">
  <img src="https://github.com/brendancjz/tompang/blob/main/featureScreenshots/Tompang-QRCode2.jpg" style="width: 300px">
</div>


## Getting Started Running Locally

### Clone this repository

Use `gh repo clone brendancjz/tompang` to get the files within this repository onto your local machine

### Environment Setup

1. Create a new database `tompang`.  
2. Change user.properties name in `FILES > Tompang-ejb > nbproject > private > private.xml`
3. Change username or password in glassfish-resources.xml to your own
4. Create and update your `alternatedocroot_1` `value` in `glassfish-web.xml`. `FILES > Tompang-war > web > WEB-INF > glassfish-web.xml`
5. Create and update your `alternatedocroot_1` `value` in `web.xml` as well. `FILES > Tompang-war > web > WEB-INF > web.xml`
6. Start glassfish server. Open Tompang project, including required projects. Add the corresponding Libraries. `Clean and Build > Deploy`  
7. In `tompang-app`, install relevant plugins with `npm install`. 
8. Create `environment.prod.ts` in `environments` with `IP_ADDRESS`. 
9. To run Tompang mobile app on `localhost`, `ionic serve`.

## File Tree 
```📦src
 ┣ 📂app
 ┃ ┣ 📂animations
 ┃ ┃ ┗ 📜nav-animation.ts
 ┃ ┣ 📂blog
 ┃ ┃ ┣ 📜blog-routing.module.ts
 ┃ ┃ ┣ 📜blog.module.ts
 ┃ ┃ ┣ 📜blog.page.html
 ┃ ┃ ┣ 📜blog.page.scss
 ┃ ┃ ┣ 📜blog.page.spec.ts
 ┃ ┃ ┗ 📜blog.page.ts
 ┃ ┣ 📂components
 ┃ ┃ ┣ 📂blog-card
 ┃ ┃ ┃ ┣ 📜blog-card-routing.module.ts
 ┃ ┃ ┃ ┣ 📜blog-card.module.ts
 ┃ ┃ ┃ ┣ 📜blog-card.page.html
 ┃ ┃ ┃ ┣ 📜blog-card.page.scss
 ┃ ┃ ┃ ┣ 📜blog-card.page.spec.ts
 ┃ ┃ ┃ ┗ 📜blog-card.page.ts
 ┃ ┃ ┣ 📂conversation-item
 ┃ ┃ ┃ ┣ 📜conversation-item-routing.module.ts
 ┃ ┃ ┃ ┣ 📜conversation-item.module.ts
 ┃ ┃ ┃ ┣ 📜conversation-item.page.html
 ┃ ┃ ┃ ┣ 📜conversation-item.page.scss
 ┃ ┃ ┃ ┣ 📜conversation-item.page.spec.ts
 ┃ ┃ ┃ ┗ 📜conversation-item.page.ts
 ┃ ┃ ┣ 📂listing-card
 ┃ ┃ ┃ ┣ 📜listing-card-routing.module.ts
 ┃ ┃ ┃ ┣ 📜listing-card.module.ts
 ┃ ┃ ┃ ┣ 📜listing-card.page.html
 ┃ ┃ ┃ ┣ 📜listing-card.page.scss
 ┃ ┃ ┃ ┣ 📜listing-card.page.spec.ts
 ┃ ┃ ┃ ┗ 📜listing-card.page.ts
 ┃ ┃ ┗ 📂user-item
 ┃ ┃ ┃ ┣ 📜user-item-routing.module.ts
 ┃ ┃ ┃ ┣ 📜user-item.module.ts
 ┃ ┃ ┃ ┣ 📜user-item.page.html
 ┃ ┃ ┃ ┣ 📜user-item.page.scss
 ┃ ┃ ┃ ┣ 📜user-item.page.spec.ts
 ┃ ┃ ┃ ┗ 📜user-item.page.ts
 ┃ ┣ 📂confirm-transaction
 ┃ ┃ ┣ 📜confirm-transaction-routing.module.ts
 ┃ ┃ ┣ 📜confirm-transaction.module.ts
 ┃ ┃ ┣ 📜confirm-transaction.page.html
 ┃ ┃ ┣ 📜confirm-transaction.page.scss
 ┃ ┃ ┣ 📜confirm-transaction.page.spec.ts
 ┃ ┃ ┗ 📜confirm-transaction.page.ts
 ┃ ┣ 📂create-listing
 ┃ ┃ ┣ 📜create-listing-routing.module.ts
 ┃ ┃ ┣ 📜create-listing.module.ts
 ┃ ┃ ┣ 📜create-listing.page.html
 ┃ ┃ ┣ 📜create-listing.page.scss
 ┃ ┃ ┣ 📜create-listing.page.spec.ts
 ┃ ┃ ┗ 📜create-listing.page.ts
 ┃ ┣ 📂create-transaction
 ┃ ┃ ┣ 📜create-transaction-routing.module.ts
 ┃ ┃ ┣ 📜create-transaction.module.ts
 ┃ ┃ ┣ 📜create-transaction.page.html
 ┃ ┃ ┣ 📜create-transaction.page.scss
 ┃ ┃ ┣ 📜create-transaction.page.spec.ts
 ┃ ┃ ┗ 📜create-transaction.page.ts
 ┃ ┣ 📂edit-listing-page
 ┃ ┃ ┣ 📜edit-listing-page-routing.module.ts
 ┃ ┃ ┣ 📜edit-listing-page.module.ts
 ┃ ┃ ┣ 📜edit-listing-page.page.html
 ┃ ┃ ┣ 📜edit-listing-page.page.scss
 ┃ ┃ ┣ 📜edit-listing-page.page.spec.ts
 ┃ ┃ ┗ 📜edit-listing-page.page.ts
 ┃ ┣ 📂footer
 ┃ ┃ ┣ 📜footer-routing.module.ts
 ┃ ┃ ┣ 📜footer.module.ts
 ┃ ┃ ┣ 📜footer.page.html
 ┃ ┃ ┣ 📜footer.page.scss
 ┃ ┃ ┣ 📜footer.page.spec.ts
 ┃ ┃ ┗ 📜footer.page.ts
 ┃ ┣ 📂header
 ┃ ┃ ┣ 📜header-routing.module.ts
 ┃ ┃ ┣ 📜header.module.ts
 ┃ ┃ ┣ 📜header.page.html
 ┃ ┃ ┣ 📜header.page.scss
 ┃ ┃ ┣ 📜header.page.spec.ts
 ┃ ┃ ┗ 📜header.page.ts
 ┃ ┣ 📂help
 ┃ ┃ ┣ 📂for-buyers
 ┃ ┃ ┃ ┣ 📜for-buyers-routing.module.ts
 ┃ ┃ ┃ ┣ 📜for-buyers.module.ts
 ┃ ┃ ┃ ┣ 📜for-buyers.page.html
 ┃ ┃ ┃ ┣ 📜for-buyers.page.scss
 ┃ ┃ ┃ ┣ 📜for-buyers.page.spec.ts
 ┃ ┃ ┃ ┗ 📜for-buyers.page.ts
 ┃ ┃ ┣ 📂for-sellers
 ┃ ┃ ┃ ┣ 📜for-sellers-routing.module.ts
 ┃ ┃ ┃ ┣ 📜for-sellers.module.ts
 ┃ ┃ ┃ ┣ 📜for-sellers.page.html
 ┃ ┃ ┃ ┣ 📜for-sellers.page.scss
 ┃ ┃ ┃ ┣ 📜for-sellers.page.spec.ts
 ┃ ┃ ┃ ┗ 📜for-sellers.page.ts
 ┃ ┃ ┣ 📜help-routing.module.ts
 ┃ ┃ ┣ 📜help.module.ts
 ┃ ┃ ┣ 📜help.page.html
 ┃ ┃ ┣ 📜help.page.scss
 ┃ ┃ ┣ 📜help.page.spec.ts
 ┃ ┃ ┗ 📜help.page.ts
 ┃ ┣ 📂history
 ┃ ┃ ┣ 📂disputes
 ┃ ┃ ┃ ┣ 📜disputes-routing.module.ts
 ┃ ┃ ┃ ┣ 📜disputes.module.ts
 ┃ ┃ ┃ ┣ 📜disputes.page.html
 ┃ ┃ ┃ ┣ 📜disputes.page.scss
 ┃ ┃ ┃ ┣ 📜disputes.page.spec.ts
 ┃ ┃ ┃ ┗ 📜disputes.page.ts
 ┃ ┃ ┣ 📂my-purchases
 ┃ ┃ ┃ ┣ 📜my-purchases-routing.module.ts
 ┃ ┃ ┃ ┣ 📜my-purchases.module.ts
 ┃ ┃ ┃ ┣ 📜my-purchases.page.html
 ┃ ┃ ┃ ┣ 📜my-purchases.page.scss
 ┃ ┃ ┃ ┣ 📜my-purchases.page.spec.ts
 ┃ ┃ ┃ ┗ 📜my-purchases.page.ts
 ┃ ┃ ┣ 📜history-routing.module.ts
 ┃ ┃ ┣ 📜history.module.ts
 ┃ ┃ ┣ 📜history.page.html
 ┃ ┃ ┣ 📜history.page.scss
 ┃ ┃ ┣ 📜history.page.spec.ts
 ┃ ┃ ┗ 📜history.page.ts
 ┃ ┣ 📂inbox
 ┃ ┃ ┣ 📂buying
 ┃ ┃ ┃ ┣ 📜buying-routing.module.ts
 ┃ ┃ ┃ ┣ 📜buying.module.ts
 ┃ ┃ ┃ ┣ 📜buying.page.html
 ┃ ┃ ┃ ┣ 📜buying.page.scss
 ┃ ┃ ┃ ┣ 📜buying.page.spec.ts
 ┃ ┃ ┃ ┗ 📜buying.page.ts
 ┃ ┃ ┣ 📂notifications
 ┃ ┃ ┃ ┣ 📜notifications-routing.module.ts
 ┃ ┃ ┃ ┣ 📜notifications.module.ts
 ┃ ┃ ┃ ┣ 📜notifications.page.html
 ┃ ┃ ┃ ┣ 📜notifications.page.scss
 ┃ ┃ ┃ ┣ 📜notifications.page.spec.ts
 ┃ ┃ ┃ ┗ 📜notifications.page.ts
 ┃ ┃ ┣ 📂selling
 ┃ ┃ ┃ ┣ 📜selling-routing.module.ts
 ┃ ┃ ┃ ┣ 📜selling.module.ts
 ┃ ┃ ┃ ┣ 📜selling.page.html
 ┃ ┃ ┃ ┣ 📜selling.page.scss
 ┃ ┃ ┃ ┣ 📜selling.page.spec.ts
 ┃ ┃ ┃ ┗ 📜selling.page.ts
 ┃ ┃ ┣ 📜inbox-routing.module.ts
 ┃ ┃ ┣ 📜inbox.module.ts
 ┃ ┃ ┣ 📜inbox.page.html
 ┃ ┃ ┣ 📜inbox.page.scss
 ┃ ┃ ┣ 📜inbox.page.spec.ts
 ┃ ┃ ┗ 📜inbox.page.ts
 ┃ ┣ 📂index
 ┃ ┃ ┣ 📜index-routing.module.ts
 ┃ ┃ ┣ 📜index.module.ts
 ┃ ┃ ┣ 📜index.page.html
 ┃ ┃ ┣ 📜index.page.scss
 ┃ ┃ ┣ 📜index.page.spec.ts
 ┃ ┃ ┗ 📜index.page.ts
 ┃ ┣ 📂liked-listings
 ┃ ┃ ┣ 📜liked-listings-routing.module.ts
 ┃ ┃ ┣ 📜liked-listings.module.ts
 ┃ ┃ ┣ 📜liked-listings.page.html
 ┃ ┃ ┣ 📜liked-listings.page.scss
 ┃ ┃ ┣ 📜liked-listings.page.spec.ts
 ┃ ┃ ┗ 📜liked-listings.page.ts
 ┃ ┣ 📂models
 ┃ ┃ ┣ 📜CategoryEnum.ts
 ┃ ┃ ┣ 📜conversation.spec.ts
 ┃ ┃ ┣ 📜conversation.ts
 ┃ ┃ ┣ 📜create-creditcard-req.spec.ts
 ┃ ┃ ┣ 📜create-creditcard-req.ts
 ┃ ┃ ┣ 📜create-dispute-req.spec.ts
 ┃ ┃ ┣ 📜create-dispute-req.ts
 ┃ ┃ ┣ 📜create-listing-req.spec.ts
 ┃ ┃ ┣ 📜create-listing-req.ts
 ┃ ┃ ┣ 📜create-transaction-req.spec.ts
 ┃ ┃ ┣ 📜create-transaction-req.ts
 ┃ ┃ ┣ 📜creditCard.spec.ts
 ┃ ┃ ┣ 📜creditCard.ts
 ┃ ┃ ┣ 📜dispute.spec.ts
 ┃ ┃ ┣ 📜dispute.ts
 ┃ ┃ ┣ 📜listing.spec.ts
 ┃ ┃ ┣ 📜listing.ts
 ┃ ┃ ┣ 📜message.spec.ts
 ┃ ┃ ┣ 📜message.ts
 ┃ ┃ ┣ 📜new-conversation-req.spec.ts
 ┃ ┃ ┣ 📜new-conversation-req.ts
 ┃ ┃ ┣ 📜new-message-req.spec.ts
 ┃ ┃ ┣ 📜new-message-req.ts
 ┃ ┃ ┣ 📜transaction.spec.ts
 ┃ ┃ ┣ 📜transaction.ts
 ┃ ┃ ┣ 📜update-transaction-req.spec.ts
 ┃ ┃ ┣ 📜update-transaction-req.ts
 ┃ ┃ ┣ 📜update-user-req.spec.ts
 ┃ ┃ ┣ 📜update-user-req.ts
 ┃ ┃ ┣ 📜user.spec.ts
 ┃ ┃ ┗ 📜user.ts
 ┃ ┣ 📂profile
 ┃ ┃ ┣ 📜profile-routing.module.ts
 ┃ ┃ ┣ 📜profile.module.ts
 ┃ ┃ ┣ 📜profile.page.html
 ┃ ┃ ┣ 📜profile.page.scss
 ┃ ┃ ┣ 📜profile.page.spec.ts
 ┃ ┃ ┗ 📜profile.page.ts
 ┃ ┣ 📂services
 ┃ ┃ ┣ 📜conversation.service.spec.ts
 ┃ ┃ ┣ 📜conversation.service.ts
 ┃ ┃ ┣ 📜dispute.service.spec.ts
 ┃ ┃ ┣ 📜dispute.service.ts
 ┃ ┃ ┣ 📜fileUpload.service.spec.ts
 ┃ ┃ ┣ 📜fileUpload.service.ts
 ┃ ┃ ┣ 📜listing.service.spec.ts
 ┃ ┃ ┣ 📜listing.service.ts
 ┃ ┃ ┣ 📜session.service.spec.ts
 ┃ ┃ ┣ 📜session.service.ts
 ┃ ┃ ┣ 📜transaction.service.spec.ts
 ┃ ┃ ┣ 📜transaction.service.ts
 ┃ ┃ ┣ 📜user.service.spec.ts
 ┃ ┃ ┗ 📜user.service.ts
 ┃ ┣ 📂settings
 ┃ ┃ ┣ 📂change-password
 ┃ ┃ ┃ ┣ 📜change-password-routing.module.ts
 ┃ ┃ ┃ ┣ 📜change-password.module.ts
 ┃ ┃ ┃ ┣ 📜change-password.page.html
 ┃ ┃ ┃ ┣ 📜change-password.page.scss
 ┃ ┃ ┃ ┣ 📜change-password.page.spec.ts
 ┃ ┃ ┃ ┗ 📜change-password.page.ts
 ┃ ┃ ┣ 📂change-profile-pic
 ┃ ┃ ┃ ┣ 📜change-profile-pic-routing.module.ts
 ┃ ┃ ┃ ┣ 📜change-profile-pic.module.ts
 ┃ ┃ ┃ ┣ 📜change-profile-pic.page.html
 ┃ ┃ ┃ ┣ 📜change-profile-pic.page.scss
 ┃ ┃ ┃ ┣ 📜change-profile-pic.page.spec.ts
 ┃ ┃ ┃ ┗ 📜change-profile-pic.page.ts
 ┃ ┃ ┣ 📂edit-profile
 ┃ ┃ ┃ ┣ 📜edit-profile-routing.module.ts
 ┃ ┃ ┃ ┣ 📜edit-profile.module.ts
 ┃ ┃ ┃ ┣ 📜edit-profile.page.html
 ┃ ┃ ┃ ┣ 📜edit-profile.page.scss
 ┃ ┃ ┃ ┣ 📜edit-profile.page.spec.ts
 ┃ ┃ ┃ ┗ 📜edit-profile.page.ts
 ┃ ┃ ┣ 📂manage-credit-cards
 ┃ ┃ ┃ ┣ 📜manage-credit-cards-routing.module.ts
 ┃ ┃ ┃ ┣ 📜manage-credit-cards.module.ts
 ┃ ┃ ┃ ┣ 📜manage-credit-cards.page.html
 ┃ ┃ ┃ ┣ 📜manage-credit-cards.page.scss
 ┃ ┃ ┃ ┣ 📜manage-credit-cards.page.spec.ts
 ┃ ┃ ┃ ┗ 📜manage-credit-cards.page.ts
 ┃ ┃ ┣ 📂manage-wallet
 ┃ ┃ ┃ ┣ 📜manage-wallet-routing.module.ts
 ┃ ┃ ┃ ┣ 📜manage-wallet.module.ts
 ┃ ┃ ┃ ┣ 📜manage-wallet.page.html
 ┃ ┃ ┃ ┣ 📜manage-wallet.page.scss
 ┃ ┃ ┃ ┣ 📜manage-wallet.page.spec.ts
 ┃ ┃ ┃ ┗ 📜manage-wallet.page.ts
 ┃ ┃ ┣ 📜settings-routing.module.ts
 ┃ ┃ ┣ 📜settings.module.ts
 ┃ ┃ ┣ 📜settings.page.html
 ┃ ┃ ┣ 📜settings.page.scss
 ┃ ┃ ┣ 📜settings.page.spec.ts
 ┃ ┃ ┗ 📜settings.page.ts
 ┃ ┣ 📂shop
 ┃ ┃ ┣ 📜shop-routing.module.ts
 ┃ ┃ ┣ 📜shop.module.ts
 ┃ ┃ ┣ 📜shop.page.html
 ┃ ┃ ┣ 📜shop.page.scss
 ┃ ┃ ┣ 📜shop.page.spec.ts
 ┃ ┃ ┗ 📜shop.page.ts
 ┃ ┣ 📂view-conversation
 ┃ ┃ ┣ 📜view-conversation-routing.module.ts
 ┃ ┃ ┣ 📜view-conversation.module.ts
 ┃ ┃ ┣ 📜view-conversation.page.html
 ┃ ┃ ┣ 📜view-conversation.page.scss
 ┃ ┃ ┣ 📜view-conversation.page.spec.ts
 ┃ ┃ ┗ 📜view-conversation.page.ts
 ┃ ┣ 📂view-credit-card-details
 ┃ ┃ ┣ 📜view-credit-card-details-routing.module.ts
 ┃ ┃ ┣ 📜view-credit-card-details.module.ts
 ┃ ┃ ┣ 📜view-credit-card-details.page.html
 ┃ ┃ ┣ 📜view-credit-card-details.page.scss
 ┃ ┃ ┣ 📜view-credit-card-details.page.spec.ts
 ┃ ┃ ┗ 📜view-credit-card-details.page.ts
 ┃ ┣ 📂view-dispute-details
 ┃ ┃ ┣ 📜view-dispute-details-routing.module.ts
 ┃ ┃ ┣ 📜view-dispute-details.module.ts
 ┃ ┃ ┣ 📜view-dispute-details.page.html
 ┃ ┃ ┣ 📜view-dispute-details.page.scss
 ┃ ┃ ┣ 📜view-dispute-details.page.spec.ts
 ┃ ┃ ┗ 📜view-dispute-details.page.ts
 ┃ ┣ 📂view-followers
 ┃ ┃ ┣ 📜view-followers-routing.module.ts
 ┃ ┃ ┣ 📜view-followers.module.ts
 ┃ ┃ ┣ 📜view-followers.page.html
 ┃ ┃ ┣ 📜view-followers.page.scss
 ┃ ┃ ┣ 📜view-followers.page.spec.ts
 ┃ ┃ ┗ 📜view-followers.page.ts
 ┃ ┣ 📂view-following
 ┃ ┃ ┣ 📜view-following-routing.module.ts
 ┃ ┃ ┣ 📜view-following.module.ts
 ┃ ┃ ┣ 📜view-following.page.html
 ┃ ┃ ┣ 📜view-following.page.scss
 ┃ ┃ ┣ 📜view-following.page.spec.ts
 ┃ ┃ ┗ 📜view-following.page.ts
 ┃ ┣ 📂view-listing-details
 ┃ ┃ ┣ 📜view-listing-details-routing.module.ts
 ┃ ┃ ┣ 📜view-listing-details.module.ts
 ┃ ┃ ┣ 📜view-listing-details.page.html
 ┃ ┃ ┣ 📜view-listing-details.page.scss
 ┃ ┃ ┣ 📜view-listing-details.page.spec.ts
 ┃ ┃ ┗ 📜view-listing-details.page.ts
 ┃ ┣ 📂view-transaction-details
 ┃ ┃ ┣ 📜view-transaction-details-routing.module.ts
 ┃ ┃ ┣ 📜view-transaction-details.module.ts
 ┃ ┃ ┣ 📜view-transaction-details.page.html
 ┃ ┃ ┣ 📜view-transaction-details.page.scss
 ┃ ┃ ┣ 📜view-transaction-details.page.spec.ts
 ┃ ┃ ┗ 📜view-transaction-details.page.ts
 ┃ ┣ 📂view-users-like-listing
 ┃ ┃ ┣ 📜view-users-like-listing-routing.module.ts
 ┃ ┃ ┣ 📜view-users-like-listing.module.ts
 ┃ ┃ ┣ 📜view-users-like-listing.page.html
 ┃ ┃ ┣ 📜view-users-like-listing.page.scss
 ┃ ┃ ┣ 📜view-users-like-listing.page.spec.ts
 ┃ ┃ ┗ 📜view-users-like-listing.page.ts
 ┃ ┣ 📜app-routing.module.ts
 ┃ ┣ 📜app.component.html
 ┃ ┣ 📜app.component.scss
 ┃ ┣ 📜app.component.spec.ts
 ┃ ┣ 📜app.component.ts
 ┃ ┗ 📜app.module.ts
 ┣ 📂assets
 ┃ ┣ 📂icon
 ┃ ┃ ┗ 📜favicon.ico
 ┃ ┗ 📂images
 ┃ ┃ ┣ 📂uploadedFiles
 ┃ ┃ ┃ ┗ 📂blogs
 ┃ ┃ ┃ ┃ ┣ 📜dean_deluca_coffee.jpg
 ┃ ┃ ┃ ┃ ┣ 📜dodol.jpg
 ┃ ┃ ┃ ┃ ┣ 📜gotochi_goods.jpg
 ┃ ┃ ┃ ┃ ┣ 📜gotochi_goods.webp
 ┃ ┃ ┃ ┃ ┣ 📜merlion.jpg
 ┃ ┃ ┃ ┃ ┣ 📜omamori_charms.jpg
 ┃ ┃ ┃ ┃ ┣ 📜omamori_charms.webp
 ┃ ┃ ┃ ┃ ┣ 📜sabon_sea_salt_scrubs.jpg
 ┃ ┃ ┃ ┃ ┣ 📜sake.jpg
 ┃ ┃ ┃ ┃ ┣ 📜seaweed.jpg
 ┃ ┃ ┃ ┃ ┣ 📜soju.jpg
 ┃ ┃ ┃ ┃ ┣ 📜songket.jpg
 ┃ ┃ ┃ ┃ ┗ 📜yankees_pillow_pets.jpg
 ┃ ┃ ┣ 📜tompang_icon_logo_blue.png
 ┃ ┃ ┣ 📜tompang_icon_logo_white.png
 ┃ ┃ ┣ 📜tompang_logo_blue.png
 ┃ ┃ ┗ 📜tompang_logo_white.png
 ┣ 📂environments
 ┃ ┣ 📜environment.prod.ts
 ┃ ┗ 📜environment.ts
 ┣ 📂theme
 ┃ ┗ 📜variables.scss
 ┣ 📜global.scss
 ┣ 📜index.html
 ┣ 📜main.ts
 ┣ 📜polyfills.ts
 ┣ 📜test.ts
 ┗ 📜zone-flags.ts ```
