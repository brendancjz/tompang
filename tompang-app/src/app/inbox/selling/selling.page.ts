import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';
import { Listing } from 'src/app/models/listing';

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

      //Creating a sample Conversation;
      const sampleConvo = new Conversation(1);
      sampleConvo.createdBy = new User(5, 'Bob','Zimmermann','ladyfinger','password',
      'bob.zim@gmail.com',new Date(),'/uploadedFiles/default_picture.jpg',54631212);
      sampleConvo.listing = new Listing(5,'Singapore','Singapore',
      'Keyboard Mechanical Tokyo Japan HOT Fire','Hello there mate. Would you like to buy this dumbbell set from Singapore?',
      'GIFTS',40.00,new Date(),2);
      this.sellingConvos = [sampleConvo, sampleConvo, sampleConvo];

    }

  ngOnInit() {
  }
}
