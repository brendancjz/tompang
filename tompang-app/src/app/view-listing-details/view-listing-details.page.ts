import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { AlertController } from '@ionic/angular';
import { Location } from '@angular/common';

@Component({
  selector: 'app-view-listing-details',
  templateUrl: './view-listing-details.page.html',
  styleUrls: ['./view-listing-details.page.scss'],
})
export class ViewListingDetailsPage implements OnInit {
  listingId: string | null;
  listingToView: Listing | null;
  retrieveListingError: boolean;

  hasLoaded: boolean;

  currentUser: User;

  listingIsLiked: boolean;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  confirmDelete: boolean;
  confirmConfirmDeleted: boolean;

  constructor(
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService,
    private alertController: AlertController
  ) {}

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewListingDetails');
    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');
    this.currentUser = this.sessionService.getCurrentUser();

    if (this.listingId != null) {
      this.listingService
        .getListingByListingId(Number(this.listingId))
        .subscribe({
          next: (response) => {
            this.listingToView = response;
            this.hasLoaded = true;
            this.checkLikedByUser();
          },
          error: (error) => {
            this.retrieveListingError = true;
            console.log('********** View Listing Details Page.ts: ' + error);
            this.location.back();
          },
        });
    }
  }

  doViewListOfUsersWhoLikeListing() {
    this.router.navigate(['/view-users-like-listing/' + this.listingId]);
  }

  getPhotoUrl(photo: string) {
    return this.sessionService.getImageBaseUrl() + photo;
  }

  formatListingCreatedBy(): string {
    return this.listingToView.createdBy.username;
  }

  formatListingCategory(): string {
    const category = this.listingToView.category;
    return category.charAt(0) + category.substring(1).toLowerCase();
  }

  doViewUserDetails(): void {
    console.log('Viewing user profile');
    const userToView = this.listingToView.createdBy;
    this.router.navigate(['/profile/' + userToView.userId]);

    //Currently not working because of the user unmarshalling error
  }

  doViewConversationDetails(): void {
    console.log('View Listing Conversation..');

    const currentUser = this.sessionService.getCurrentUser();

    let convo: Conversation;
    let buyingConvos: Conversation[];
    let convoNotFound: boolean;
    convoNotFound = true;

    //Have a current convo
    // eslint-disable-next-line radix
    this.conversationService.retrieveBuyerConversations().subscribe({
      next: (response) => {
        buyingConvos = response;
        // check to see if there is existing convo
        // eslint-disable-next-line @typescript-eslint/prefer-for-of
        for (let i = 0; i < buyingConvos.length; i++) {
          console.log(buyingConvos);
          console.log(this.listingToView);
          console.log('enter for loop');
          if (
            buyingConvos[i].listing.listingId === this.listingToView.listingId
          ) {
            console.log('convo found');
            convoNotFound = false;
            convo = buyingConvos[i];
            this.router.navigate(['/view-conversation/' + convo.convoId]);
            break;
          }
        }
        if (convoNotFound) {
          console.log('need to cr8 new convo');
          //after for loop no existing convo le. need create new
          convo = new Conversation();
          convo.createdBy = this.sessionService.getCurrentUser();
          convo.listing = this.listingToView;
          convo.buyerUnread = 0;
          convo.sellerUnread = 0;
          convo.seller = this.listingToView.createdBy;
          convo.messages = new Array();
          convo.isOpen = true;
          this.conversationService
            .createConversation(
              convo,
              Number(this.listingToView.listingId),
              Number(this.sessionService.getCurrentUser().userId)
            )
            .subscribe({
              // eslint-disable-next-line @typescript-eslint/no-shadow
              next: (response) => {
                const newConversationId: number = response;
                this.resultSuccess = true;
                this.resultError = false;
                this.message =
                  'New Conversation ' +
                  newConversationId +
                  ' created successfully';
                this.router.navigate([
                  '/view-conversation/' + newConversationId,
                ]);
              },
              error: (error) => {
                this.resultError = true;
                this.resultSuccess = false;
                this.message =
                  'An error has occurred while creating the new conversation: ' +
                  error;
                console.log('********** createNewConversation: ' + error);
              },
            });
        }
      },

      error: (error) => {
        console.log('view-listing-chat-now (no buying convos at all):' + error);
        convo = new Conversation();
        convo.createdBy = this.sessionService.getCurrentUser();
        convo.listing = this.listingToView;
        convo.buyerUnread = 0;
        convo.sellerUnread = 0;
        convo.seller = this.listingToView.createdBy;
        convo.messages = new Array();
        convo.isOpen = true;
        this.conversationService
          .createConversation(
            convo,
            Number(this.listingToView.listingId),
            Number(this.sessionService.getCurrentUser().userId)
          )
          .subscribe({
            next: (response) => {
              console.log('service method success');
              const newConversationId: number = response;
              this.resultSuccess = true;
              this.resultError = false;
              this.message = 'Successfully created a conversation';
              this.router.navigate(['/view-conversation/' + newConversationId]);
              console.log(
                'New Conversation ' +
                  newConversationId +
                  ' created successfully'
              );
            },
            // eslint-disable-next-line @typescript-eslint/no-shadow
            error: (error) => {
              this.resultError = true;
              this.resultSuccess = false;
              this.message = 'Unexpected error occured. Try again later';
              console.log('********** createNewConversation: ' + error);
              console.log(
                'An error has occurred while creating the new conversation: ' +
                  error
              );
            },
          });
      },
    });
  }

  isCurrentUserTheCreatorOfThisListing() {
    return this.listingToView.createdBy.username === this.currentUser.username;
  }

  makeTransaction(): void {
    console.log('Make transaction..');
    this.router.navigate(['/create-transaction/' + this.listingId]);
  }

  likeListing(): void {
    this.listingService.likeListing(this.listingToView).subscribe({
      next: (response) => {
        console.log('listing liked!');
      },
      error: (error) => {
        console.log('view-listing-card.page.ts:' + error);
        console.log('FAIL');
      },
    });

    this.listingToView.likedByUsers.length += 1;
    this.listingIsLiked = true;
  }

  unlikeListing(): void {
    this.listingService.unlikeListing(this.listingToView).subscribe({
      next: (response) => {
        console.log('listing unliked!');
      },
      error: (error) => {
        console.log('view-listing-card.page.ts:' + error);
        console.log('FAIL');
      },
    });

    this.listingToView.likedByUsers.length -= 1;
    this.listingIsLiked = false;
  }

  checkLikedByUser(): void {
    this.listingToView.likedByUsers.map((likedUser) => {
      if (this.currentUser.userId === likedUser.userId) {
        this.listingIsLiked = true;
      }
    });
  }

  formatListingTitle(): string {
    const maxNumberBeforeCutOff = 30;
    if (this.listingToView.title.length >= maxNumberBeforeCutOff) {
      return (
        this.listingToView.title.substring(0, maxNumberBeforeCutOff - 3) + '...'
      );
    }

    return this.listingToView.title;
  }

  editListing() {
    this.router.navigate(['/edit-listing-page/' + this.listingId]);
  }

  async deleteListing() {
    console.log('Deleting Listing');

    const alert = await this.alertController.create({
      header: 'Confirm Delete Listing',
      message: 'Confirm delete listing <strong>' + this.listingToView.title + '</strong>?',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {

          }
        }, {
          text: 'Okay',
          handler: () => {

            this.listingService.deleteListing(Number(this.listingId)).subscribe({
              next:(response)=>{
                this.confirmDelete = true;
                this.listingToView = null;
                this.confirmConfirmDeleted = true;
                console.log('success');
              },
              error:(error)=>{
                this.resultError = true;
                this.message = error;

              }
            });
          }
        }
      ]
    });

    await alert.present();
  }

  toggleConfirmDeleteListing() {
    this.confirmDelete = true;
  }
}
