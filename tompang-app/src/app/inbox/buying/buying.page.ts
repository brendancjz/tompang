import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';
import { Listing } from 'src/app/models/listing';

@Component({
  selector: 'app-buying',
  templateUrl: './buying.page.html',
  styleUrls: ['./buying.page.scss'],
})
export class BuyingPage implements OnInit {
  buyingConvos: Conversation[];

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {

      //Creating a sample Conversation;
      const sampleConvo = new Conversation(1);
      sampleConvo.createdBy = new User(5, 'Bob','Zimmermann','bobzimmer','password',
      'bob.zim@gmail.com',new Date(),'/uploadedFiles/default_picture.jpg',54631212);
      sampleConvo.listing = new Listing(5,'Singapore','Singapore',
      'Dumbbell Set','Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS',40.00,new Date(),2);
      this.buyingConvos = [sampleConvo, sampleConvo, sampleConvo];

    }

  ngOnInit() {
  }

  getUserProfilePic(convo: Conversation): string {
    const baseUrl = '../../assets/images';
    return baseUrl + convo.createdBy.profilePic;
  }

  getLatestMessageDateOfConvo(convo: Conversation): string {
    //Code to get the latest message

    return '12/03/2022';
  }

  viewConversation(convo: Conversation) {
    console.log('Viewing buying conversation...');
  }
}
