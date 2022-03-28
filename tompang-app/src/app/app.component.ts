import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  public appPages = [
    { title: 'Profile', url: '', icon: 'person' },
    { title: 'Shop', url: '', icon: 'gift' },
    { title: 'Inbox', url: '', icon: 'chatbubbles' },
    { title: 'History', url: '', icon: 'receipt' },
    { title: 'Setting', url: '', icon: 'settings' },
  ];

  constructor() {}
}
