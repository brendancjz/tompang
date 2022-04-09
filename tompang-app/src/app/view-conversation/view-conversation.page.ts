import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { Message } from '../models/message';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-view-conversation',
  templateUrl: './view-conversation.page.html',
  styleUrls: ['./view-conversation.page.scss'],
})
export class ViewConversationPage implements OnInit {

  convoId: string | null;
  convoToView: Conversation | null;
  retrieveConvoError: boolean;
  newMessageBody: string | null;

  hasLoaded: boolean;

  currentUser: User | null;
  currentUserIsTalkingTo: User | null;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  @ViewChild('convoblock') private convoblock: any;
  @ViewChild('convoblocklist') private convoblocklist: any;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private userService: UserService,
    private conversationService: ConversationService) { }

  ngOnInit() {
    this.convoId = this.activatedRoute.snapshot.paramMap.get('convoId');
    this.currentUser = this.sessionService.getCurrentUser();

    if (this.convoId != null) {
      console.log('View conversation: ' + this.convoId);

      this.conversationService.getConversationById(parseInt(this.convoId)).subscribe({
        next: (response) => {
          this.convoToView = response;
          console.log(response);
          console.log(this.convoToView);
          this.hasLoaded = true;
          console.log("PASS");
        },
        error: (error) => {
          console.log('view-conversation.page.ts:' + error);
          console.log("FAIL");
        },
      });


      //Implement bridge with try catch
      // eslint-disable-next-line radix

      console.log('after service');
      console.log(this.convoId);
      console.log(this.convoToView);
      // console.log(this.convoToView.convoId);
      // if (this.convoToView.createdBy.username === this.currentUser.username) {
      //   console.log('Current user created this convo');
      // } else {
      //   console.log('Current user is the seller of this listing');
      // }

    }
  }

  getConversationListingUrl(): string {
    const basePictureUrl = '../../assets/images';
    console.log('getConversationListingUrl : ' + this.convoToView.convoId);
    return basePictureUrl + this.convoToView.listing.photos[0];
  }

  viewListing(): void {
    console.log('View listing details from convo page');
    this.router.navigate(['/view-listing-details/' + this.convoToView.listing.listingId]);
  }

  isCurrentUserTheBuyer(): boolean {
    return this.currentUser.username === this.convoToView.createdBy.username;
  }

  makeTransaction(): void {
    console.log('Make transaction..');
    this.router.navigate(['/create-transaction/' + this.convoToView.listing.listingId]);
  }

  addMessage() {
    console.log('method called');
    const newMessage = new Message();
    newMessage.body = this.newMessageBody;
    newMessage.createdOn = new Date();
    if (this.isCurrentUserTheBuyer()) {
      console.log('message from buyer');
      newMessage.fromBuyer = true;
    } else {
      newMessage.fromBuyer = false;
    }
    newMessage.offerMessage = false;
    if (this.isCurrentUserTheBuyer()) {
      newMessage.readByBuyer = true;
      newMessage.readBySeller = false;
    } else {
      newMessage.readBySeller = true;
      newMessage.readByBuyer = false;
    }
    newMessage.sentBy = this.currentUser.userId;
    console.log(newMessage);
    console.log(this.convoId);
    this.conversationService.addMessage(newMessage, (Number(this.convoId))).subscribe({
      next: (response) => {
        let newMessageId: number = response;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "New Message " + newMessageId + " created successfully";
        this.conversationService.getConversationById(parseInt(this.convoId)).subscribe({
          next: (response) => {
            this.convoToView = response;
            console.log(response);
            console.log(this.convoToView);
            this.hasLoaded = true;
            console.log("PASS");
            this.convoblock.scrollToBottom(300);
            this.convoblock.scrollToBottom(300);
            this.convoblocklist.scrollToBottom(300);
            this.convoblocklist.scrollToBottom(300);
          },
          error: (error) => {
            console.log('view-conversation.page.ts:' + error);
            console.log("FAIL");
          },
        });
      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while creating the new message: " + error;

        console.log('********** createNewMessage: ' + error);
      }
    });
    this.newMessageBody = undefined;
  }

  getUserProfilePic(indicator: number): string {
    const baseUrl = '../../assets/images';
    if (indicator == 2) {
      console.log('never enter')
      return baseUrl + this.convoToView.createdBy.profilePic;
    } else {
      console.log('enter')
      return baseUrl + this.convoToView.seller.profilePic;
    }
  }
}
