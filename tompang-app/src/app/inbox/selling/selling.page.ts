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
  sellingConvos: Conversation[] | null;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private conversationService: ConversationService) {}

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('IonViewWillEnter Selling');

    this.conversationService.retrieveSellerConversations().subscribe({
      next: (response) => {
        this.sellingConvos = response;
        // eslint-disable-next-line @typescript-eslint/prefer-for-of
        for(let i = 0; i < this.sellingConvos.length; i++) {
          const size = this.sellingConvos[i].messages.length;
          this.sellingConvos[i].lastMessageDate = this.sellingConvos[i].messages[size - 1].createdOn;
        }
      },
      error: (error) => {
        console.log('sellingPage : retrieveSellerConversations.ts:' + error);
        this.sellingConvos = null;
      },
    });
  }
}
