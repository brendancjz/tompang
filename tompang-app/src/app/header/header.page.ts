import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.page.html',
  styleUrls: ['./header.page.scss'],
})
export class HeaderPage implements OnInit {

  currentUser: User;
  username: string | undefined;

  constructor(public sessionService: SessionService) {
    this.currentUser = sessionService.getCurrentUser();
    this.username = this.currentUser.username;
   }

  ngOnInit() {
  }

}
