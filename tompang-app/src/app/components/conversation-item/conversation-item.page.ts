import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Conversation } from 'src/app/models/conversation';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-conversation-item',
  templateUrl: './conversation-item.page.html',
  styleUrls: ['./conversation-item.page.scss'],
})
export class ConversationItemPage implements OnInit {

  @Input() convo: Conversation;

  constructor(private router: Router,
    private sessionService: SessionService) { }

  ngOnInit() {
  }

  getUserProfilePic(convo: Conversation): string {
    const baseUrl = '../../assets/images';
    //Profile pic should be the user of the latest message sent
    return baseUrl + convo.createdBy.profilePic;
  }

  getLatestMessageDateOfConvo(convo: Conversation): string {
    //Code to get the latest message

    return '12/03/2022';
  }

  getListingTitleOfConvo(convo: Conversation): string {
    const maxNumberBeforeCutOff = 22;
    if (convo.listing.title.length >= maxNumberBeforeCutOff) {
      return convo.listing.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return convo.listing.title;
  }

  viewConversation(convo: Conversation) {
    console.log('Viewing buying conversation...');
    this.router.navigate(['/view-conversation/' + convo.convoId]);
  }

}
