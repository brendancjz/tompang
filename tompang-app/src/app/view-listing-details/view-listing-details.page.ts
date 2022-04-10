import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';

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

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');
    this.currentUser = this.sessionService.getCurrentUser();
    this.listingIsLiked = this.doesCurrentUserLikeThisListing();

    if (this.listingId != null) {
      this.listingService
        .getListingByListingId(Number(this.listingId))
        .subscribe({
          next: (response) => {
            this.listingToView = response;
            this.hasLoaded = true;
            console.log(this.listingToView);
          },
          error: (error) => {
            this.retrieveListingError = true;
            console.log('********** View Listing Details Page.ts: ' + error);
          },
        });

      // eslint-disable-next-line radix
      // this.listingService.getListingByListingId(parseInt(this.listingId)).subscribe({
      //   next:(response)=>{
      //     this.listingToView = response;
      //   },
      //   error:(error)=>{
      //     this.retrieveListingError = true;
      // 		console.log('********** View Listing Details Page.ts: ' + error);
      //   }
      // });
    }
  }

  ionViewDidEnter() { }

  getPhotoUrl(photo: string) {
    const baseUrl = 'http://localhost:8080/Tompang-war';
    return baseUrl + photo;
  }

  formatListingCreatedBy(): string {
    return this.listingToView.createdBy.username;
  }
  formatListingCategory(): string {
    const category = this.listingToView.category;
    return category.charAt(0) + category.substring(1).toLowerCase();
  }
  viewUserProfile(): void {
    console.log('Viewing user profile');
    const userToView = this.listingToView.createdBy;
    this.router.navigate(['/profile/' + userToView.userId]);

    //Currently not working because of the user unmarshalling error
  }

  viewListingConversation(): void {
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
          if (buyingConvos[i].listing.listingId === this.listingToView.listingId) {
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
          this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)),
            (Number(this.sessionService.getCurrentUser().userId))).subscribe({
              // eslint-disable-next-line @typescript-eslint/no-shadow
              next: (response) => {
                const newConversationId: number = response;
                this.resultSuccess = true;
                this.resultError = false;
                this.message = 'New Conversation ' + newConversationId + ' created successfully';
                this.router.navigate(['/view-conversation/' + newConversationId]);
              },
              error: (error) => {
                this.resultError = true;
                this.resultSuccess = false;
                this.message = 'An error has occurred while creating the new conversation: ' + error;
                console.log('********** createNewConversation: ' + error);
              }
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
        this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)),
          (Number(this.sessionService.getCurrentUser().userId))).subscribe({
            next: (response) => {
              console.log('service method success');
              const newConversationId: number = response;
              this.resultSuccess = true;
              this.resultError = false;
              this.message = 'Successfully created a conversation';
              this.router.navigate(['/view-conversation/' + newConversationId]);
              console.log('New Conversation ' + newConversationId + ' created successfully');
            },
            // eslint-disable-next-line @typescript-eslint/no-shadow
            error: (error) => {
              this.resultError = true;
              this.resultSuccess = false;
              this.message = 'Unexpected error occured. Try again later';
              console.log('********** createNewConversation: ' + error);
              console.log('An error has occurred while creating the new conversation: ' + error);
            }
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
    this.listingService.likeListing(this.listingToView);

    this.listingIsLiked = true;
  }

  unlikeListing(): void {
    this.listingService.unlikeListing(this.listingToView);

    this.listingIsLiked = false;
  }

  doesCurrentUserLikeThisListing(): boolean {
    // eslint-disable-next-line radix
    return this.userService.isListingLikedByUser(this.currentUser.userId, parseInt(this.listingId));
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
}

