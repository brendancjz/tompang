import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { Message } from '../models/message';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';
import { FileUploadService } from '../services/fileUpload.service';
import { Location } from '@angular/common';
import { interval, TimeInterval } from 'rxjs';

@Component({
  selector: 'app-view-conversation',
  templateUrl: './view-conversation.page.html',
  styleUrls: ['./view-conversation.page.scss'],
})
export class ViewConversationPage implements OnInit {
  @ViewChild('convoblock') convoblock: any;
  @ViewChild('convoblocklist') convoblocklist: any;
  @ViewChild('fileInput') fileInput: ElementRef;

  convoId: string | null;
  convoToView: Conversation | null;
  retrieveConvoError: boolean;
  newMessageBody: string | null;
  containsImage: boolean;
  imageToSend: File | null;

  hasLoaded: boolean;

  currentUser: User | null;
  currentUserIsTalkingTo: User | null;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;
  interval: any;

  constructor(private location: Location, private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private userService: UserService,
    private conversationService: ConversationService,
    private fileUploadService: FileUploadService) { }

  ngOnInit() { }


  ionViewWillEnter() {
    this.refreshData();
    this.doConversationRefreshTimer();
  }

  ionViewWillLeave() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  doConversationRefreshTimer() {
    this.interval = setInterval(() => {
      this.refreshData();
    }, 10000);
  }

  refreshData() {
    console.log('data is refreshed');
    this.convoId = this.activatedRoute.snapshot.paramMap.get('convoId');
    this.currentUser = this.sessionService.getCurrentUser();

    if (this.convoId != null) {
      // eslint-disable-next-line radix
      this.conversationService.getConversationById(parseInt(this.convoId)).subscribe({
        next: (response) => {
          this.convoToView = response;
          console.log('ConvoToView', this.convoToView);
          this.hasLoaded = true;
        },
        error: (error) => {
          console.log('view-conversation.page.ts:' + error);
          this.location.back();
        },
      });
    }
  }

  getConversationListingUrl(): string {
    return this.sessionService.getImageBaseUrl() + this.convoToView.listing.photos[0];
  }

  doViewListingDetails(): void {
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

  uploadPicture(event: any) {
    this.imageToSend = event.target.files.item(0);
    this.containsImage = true;
    console.log(this.imageToSend);
  }

  getImage(imageUrl: string) {
    return this.sessionService.getImageBaseUrl() + imageUrl;
  }

  doAddMessage() {
    this.fileUploadService.uploadFile(this.imageToSend).subscribe({
      next: (response) => {
        console.log(this.imageToSend);
        console.log('********** FileUploadComponent.ts: File uploaded successfully: ' + response.status);
        this.imageToSend = null;
        this.fileInput.nativeElement.value = '';
      },
      error: (error) => {
        console.log('********** FileUploadComponent.ts: File upload unsuccessful: ' + error);
      }
    });
    console.log('method called');
    const newMessage = new Message();
    if (this.newMessageBody === undefined && this.containsImage) {
      newMessage.body = 'Sending an image';
    } else {
      newMessage.body = this.newMessageBody;
    }
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
    if (this.containsImage) {
      newMessage.containsImage = true;
      newMessage.imageUrl = '/uploadedFiles/' + this.imageToSend.name;
    }
    console.log(newMessage);
    console.log(this.convoId);
    this.conversationService.addMessage(newMessage, (Number(this.convoId))).subscribe({
      next: (response) => {
        const newMessageId: number = response;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = 'New Message ' + newMessageId + ' created successfully';
        // eslint-disable-next-line radix
        this.conversationService.getConversationById(parseInt(this.convoId)).subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            this.convoToView = response;

            console.log(this.convoToView);
            this.hasLoaded = true;
            this.convoblock.scrollToBottom(300);
            this.convoblock.scrollToBottom(300);
            this.convoblocklist.scrollToBottom(300);
            this.convoblocklist.scrollToBottom(300);
          },
          error: (error) => {
            console.log('view-conversation.page.ts:' + error);
          },
        });
      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'An error has occurred while creating the new message: ' + error;

        console.log('********** createNewMessage: ' + error);
      }
    });
    this.newMessageBody = undefined;
  }

  getUserProfilePic(indicator: number): string {
    if (indicator === 2) {
      return this.sessionService.getImageBaseUrl() + this.convoToView.createdBy.profilePic;
    } else {
      return this.sessionService.getImageBaseUrl() + this.convoToView.seller.profilePic;
    }
  }

  formatListingTitle() {
    const maxNumberBeforeCutOff = 35;
    if (this.convoToView.listing.title.length >= maxNumberBeforeCutOff) {
      return this.convoToView.listing.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return this.convoToView.listing.title;
  }
}
