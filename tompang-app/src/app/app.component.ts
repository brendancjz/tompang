import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public appPages = [
    { title: 'Profile', url: '/profile', icon: 'person' },
    { title: 'Marketplace', url: '/shop', icon: 'earth' },
    { title: 'Inbox', url: '/inbox', icon: 'chatbubbles' },
    { title: 'History', url: '/history', icon: 'receipt' },
    { title: 'Settings', url: '/settings', icon: 'settings' },
    { title: 'Orders', url: '/orders', icon: 'orders' },
  ];

  constructor() {}
}
