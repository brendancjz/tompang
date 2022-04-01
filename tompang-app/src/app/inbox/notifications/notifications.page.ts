import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Transaction } from 'src/app/models/transaction';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.page.html',
  styleUrls: ['./notifications.page.scss'],
})
export class NotificationsPage implements OnInit {

  notifications: Transaction[];

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {

      const sampleTransaction = new Transaction(1, 1000);

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
}
