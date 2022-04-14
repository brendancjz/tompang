import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { ConversationService } from '../../services/conversation.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';
import { Listing } from 'src/app/models/listing';
import { Message } from 'src/app/models/message';

@Component({
  selector: 'app-buying',
  templateUrl: './buying.page.html',
  styleUrls: ['./buying.page.scss'],
})
export class BuyingPage implements OnInit {
  buyingConvos: Conversation[] | null;
  searchTerm: string;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private conversationService: ConversationService) {}

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.doViewListOfBuyingConversations();
  }

  doViewListOfBuyingConversations() {
    this.conversationService.retrieveBuyerConversations().subscribe({
      next: (response) => {
        this.buyingConvos = response;
        console.log(this.sessionService.getCurrentUser().username);
        console.log(this.sessionService.getCurrentUser().password);
        console.log(this.buyingConvos);
        // eslint-disable-next-line @typescript-eslint/prefer-for-of
        for(let i = 0; i < this.buyingConvos.length; i++) {
          const size = this.buyingConvos[i].messages.length;
          this.buyingConvos[i].lastMessageDate = this.buyingConvos[i].messages[size - 1].createdOn;
        }
      },
      error: (error) => {
        console.log('buyingPage : retrieveBuyerConversations.ts:' + error);
        this.buyingConvos = null;
      },
    });
  }
}
