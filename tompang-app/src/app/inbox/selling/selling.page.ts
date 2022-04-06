import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';
import { Listing } from 'src/app/models/listing';
import { Message } from 'src/app/models/message';

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
    private userService: UserService) {


    const initiateMessage1 = new Message(1, "Hi can I buy this?", true, sessionService.getCurrentUser().userId, true, false, false);
    const replyMessage1 = new Message(2, "Sure, you have to make an offer!", false, 50, false, true, false);
    const initiateMessage2 = new Message(3, "Hi can I buy this?", true, sessionService.getCurrentUser().userId, true, false, false);
    const replyMessage2 = new Message(4, "Sure, you have to make an offer!", false, 51, false, true, false);
    const initiateMessage3 = new Message(5, "Hi can I buy this?", true, sessionService.getCurrentUser().userId, true, false, false);
    const replyMessage3 = new Message(6, "Sure, you have to make an offer!", false, 52, false, true, false);

    //Creating a sample Conversation;
    const sampleConvo1 = new Conversation(50);
    sampleConvo1.createdBy = new User(50, 'Buyer', 'One', 'buyer1', 'password',
      'buyer1@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
    sampleConvo1.listing = new Listing(5, 'Singapore', 'Singapore',
      'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS', 40.00, new Date(), 2);
    sampleConvo1.messages.push(initiateMessage1);
    sampleConvo1.messages.push(replyMessage1);

    const sampleConvo2 = new Conversation(51);
    sampleConvo2.createdBy = new User(51, 'Buyer', 'Two', 'buyer2', 'password',
      'buyer2@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
    sampleConvo2.listing = new Listing(5, 'Singapore', 'Singapore',
      'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS', 40.00, new Date(), 2);
    sampleConvo2.messages.push(initiateMessage2);
    sampleConvo2.messages.push(replyMessage2);

    const sampleConvo3 = new Conversation(52);
    sampleConvo3.createdBy = new User(52, 'Buyer', 'Three', 'buyer3', 'password',
      'buyer3@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
    sampleConvo3.listing = new Listing(5, 'Singapore', 'Singapore',
      'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS', 40.00, new Date(), 2);
    sampleConvo3.messages.push(initiateMessage3);
    sampleConvo3.messages.push(replyMessage3);
    this.sellingConvos = [sampleConvo1, sampleConvo2, sampleConvo3];

  }

  ngOnInit() {
  }
}
