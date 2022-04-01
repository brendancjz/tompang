import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Conversation } from 'src/app/models/conversation';

@Component({
  selector: 'app-selling',
  templateUrl: './selling.page.html',
  styleUrls: ['./selling.page.scss'],
})
export class SellingPage implements OnInit {

  sellingConvos: Conversation[];

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {

      const sampleConvo = new Conversation(1,true,0,0);

      this.sellingConvos = [sampleConvo, sampleConvo, sampleConvo];
    }

  ngOnInit() {
  }

  viewConversation(convo: Conversation) {
    console.log('Viewing selling conversation...');
  }
}
