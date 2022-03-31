import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-manage-credit-cards',
  templateUrl: './manage-credit-cards.page.html',
  styleUrls: ['./manage-credit-cards.page.scss'],
})
export class ManageCreditCardsPage implements OnInit {

  constructor(private location: Location) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});
  }
  resetPage() {
    return;
  }
}
