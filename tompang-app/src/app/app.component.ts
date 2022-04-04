import { Component } from '@angular/core';
import { SessionService } from './services/session.service';
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public appPages = [];

  constructor(private sessionService: SessionService) {
    const currentUser = sessionService.getCurrentUser();
    this.appPages = [
      { title: 'Profile', url: '/profile/' + currentUser.userId, icon: 'person' },
      { title: 'Marketplace', url: '/shop', icon: 'earth' },
      { title: 'Inbox', url: '/inbox', icon: 'chatbubbles' },
      { title: 'History', url: '/history', icon: 'receipt' },
      { title: 'Settings', url: '/settings', icon: 'settings' },
      { title: 'Orders', url: '/orders', icon: 'orders' },
    ];
  }
}
