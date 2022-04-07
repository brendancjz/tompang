import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';
import { Listing } from 'src/app/models/listing';
import { Message } from 'src/app/models/message';
import { ConversationService } from '../../services/conversation.service';

@Component({
  selector: 'app-selling',
  templateUrl: './selling.page.html',
  styleUrls: ['./selling.page.scss'],
})
export class SellingPage implements OnInit {
  searchTerm: string;
  sellingConvos: Conversation[];

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private conversationService:ConversationService) {
    this.conversationService.retrieveSellerConversations().subscribe({
      next: (response) => {
        this.sellingConvos = response;
        console.log(this.sessionService.getCurrentUser().username);
        console.log(this.sessionService.getCurrentUser().password);
        console.log(this.sellingConvos);
      },
      error: (error) => {
        console.log('sellingPage : retrieveSellerConversations.ts:' + error);
      },
    });
  }

  ngOnInit() {
  }
}
