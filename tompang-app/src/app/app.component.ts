import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public appPages = [
    { title: 'Profile', url: '/folder/Profile', icon: 'person' },
    { title: 'Shop', url: '/folder/Shop', icon: 'gift' },
    { title: 'Inbox', url: '/folder/Inbox', icon: 'chatbubbles' },
    { title: 'History', url: '/folder/History', icon: 'receipt' },
    { title: 'Setting', url: '/folder/Setting', icon: 'settings' },
  ];

  constructor() {}
}
