import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-manage-credit-cards',
  templateUrl: './manage-credit-cards.page.html',
  styleUrls: ['./manage-credit-cards.page.scss'],
})
export class ManageCreditCardsPage implements OnInit {

  currentUser: User;

  constructor(private location: Location,
    public sessionService: SessionService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
  }
  resetPage() {
    return;
  }
}
