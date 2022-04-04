import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService) { }

  ngOnInit() {
    this.convoId = this.activatedRoute.snapshot.paramMap.get('convoId');

    if(this.convoId != null)
    {
      this.hasLoaded = true;
      console.log('View conversation: ' + this.convoId);

      //Implement bridge with try catch
      // eslint-disable-next-line radix
      this.convoToView = this.conversationService.getConversationById(parseInt(this.convoId));

    }
  }

}
