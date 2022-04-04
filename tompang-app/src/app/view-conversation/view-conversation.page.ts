import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-conversation',
  templateUrl: './view-conversation.page.html',
  styleUrls: ['./view-conversation.page.scss'],
})
export class ViewConversationPage implements OnInit {

  convoId: string | null;
  convoToView: Conversation | null;
  retrieveConvoError: boolean;

  hasLoaded: boolean;

  currentUser: User | null;
  currentUserIsTalkingTo: User | null;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService) { }

  ngOnInit() {
    this.convoId = this.activatedRoute.snapshot.paramMap.get('convoId');
    this.currentUser = this.sessionService.getCurrentUser();

    if(this.convoId != null)
    {
      this.hasLoaded = true;
      console.log('View conversation: ' + this.convoId);

      //Implement bridge with try catch
      // eslint-disable-next-line radix
      this.convoToView = this.conversationService.getConversationById(parseInt(this.convoId));

      if (this.convoToView.createdBy.username === this.currentUser.username) {
        console.log('Current user created this convo');
        this.currentUserIsTalkingTo = this.convoToView.listing.createdBy;
      } else {
        console.log('Current user is the seller of this listing');
        this.currentUserIsTalkingTo = this.convoToView.createdBy;
      }
    }
  }

  getConversationListingUrl(): string {
    const basePictureUrl = '../../assets/images';

    return basePictureUrl + this.convoToView.listing.photos[0];
  }

  viewListing(): void {
    console.log('View listing details from convo page');
    this.router.navigate(['/view-listing-details/' + this.convoToView.listing.listingId]);
  }

  isCurrentUserTheBuyer(): boolean {
    return this.currentUser.username === this.convoToView.createdBy.username;
  }
}
