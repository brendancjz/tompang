import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
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
  buyingConvos: Conversation[];
  searchTerm: string;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {

    //Creating a sample Conversation;
    const sampleConvo1 = new Conversation(60);
    sampleConvo1.createdBy = sessionService.getCurrentUser();
    sampleConvo1.listing = new Listing(5, 'Singapore', 'Singapore',
      'Dumbbell Set', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS', 40.00, new Date(), 2);

    this.buyingConvos = [sampleConvo1, sampleConvo1, sampleConvo1];
  }

  ngOnInit() {
  }
}
