import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/models/user';
import { CreditCard } from 'src/app/models/creditCard';


@Component({
  selector: 'app-manage-credit-cards',
  templateUrl: './manage-credit-cards.page.html',
  styleUrls: ['./manage-credit-cards.page.scss'],
})
export class ManageCreditCardsPage implements OnInit {
  currentUser: User;

  crediCardToDelete: CreditCard;
  isDisplayingCCList: boolean;

  newCreditCard: CreditCard | null;

  constructor(private location: Location,
    public sessionService: SessionService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
    this.isDisplayingCCList = true;
    this.newCreditCard = new CreditCard();
  }
  resetPage() {
    return;
  }

  viewCreditCard(creditcard: CreditCard) {
    console.log('Viewing credit card..');
  }

  addCreditCard() {
    console.log('Creating new credit card..');
    //TODO

    this.newCreditCard = null;
  }

  toggleAddCreditCardPage() {
    console.log('Adding credit card page..');
    this.isDisplayingCCList = !this.isDisplayingCCList;
  }

  formatCreditCardNumber(ccNum: number) {
    const stringNum = ccNum.toString();
    return stringNum.substring(0,4) + ' ' + stringNum.substring(4,8) +
    ' ' + stringNum.substring(8,12) + ' ' + stringNum.substring(12,16);
  }
}
