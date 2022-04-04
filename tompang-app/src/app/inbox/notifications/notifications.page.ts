import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Transaction } from 'src/app/models/transaction';
import { ConversationService } from 'src/app/services/conversation.service';
import { Router } from '@angular/router';
import { ListingService } from 'src/app/services/listing.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.page.html',
  styleUrls: ['./notifications.page.scss'],
})
export class NotificationsPage implements OnInit {

  notifications: Transaction[];

  constructor(private router: Router, private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private conversationService: ConversationService,
    private listingService: ListingService) {

      const sampleTransaction = new Transaction(1, 1000);
      sampleTransaction.buyer = sessionService.getCurrentUser();
      sampleTransaction.listing = this.listingService.getSampleListing();
      this.notifications = [sampleTransaction, sampleTransaction, sampleTransaction,
        sampleTransaction, sampleTransaction, sampleTransaction,
        sampleTransaction, sampleTransaction, sampleTransaction,
        sampleTransaction, sampleTransaction, sampleTransaction,
        sampleTransaction, sampleTransaction, sampleTransaction];
    }

  ngOnInit() {
    console.log('ngOnInit for Notifications page called.');
  }

  viewNotification(transaction: Transaction) {
    console.log('Viewing notification...');
  }

  viewConversation(transaction: Transaction) {
    console.log('Viewing conversation from notification');
    const buyer: User = transaction.buyer;
    const listing = transaction.listing;

    const convo = this.conversationService.getBuyerConversationWithListing(buyer.userId,listing.listingId);

    this.router.navigate(['/view-conversation/' + convo.convoId]);
  }
}
