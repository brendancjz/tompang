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
    //const currentUser = sessionService.getCurrentUser();
    this.appPages = [
      { title: 'Profile', url: '/profile/#', icon: 'person' },
      { title: 'Marketplace', url: '/shop', icon: 'earth' },
      { title: 'Blog', url: '/blog', icon: 'newspaper' },
      { title: 'Inbox', url: '/inbox', icon: 'chatbubbles' },
      { title: 'History', url: '/history', icon: 'receipt' },
      { title: 'Settings', url: '/settings', icon: 'settings' },
      { title: 'Help', url: '/help', icon: 'hand-right' },
      { title: 'Orders', url: '/orders', icon: 'orders' },
    ];
  }
}
