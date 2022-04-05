import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditCard } from '../models/creditCard';
import { User } from '../models/user';
import { CreditCardService } from '../services/creditCard.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-credit-card-details',
  templateUrl: './view-credit-card-details.page.html',
  styleUrls: ['./view-credit-card-details.page.scss'],
})
export class ViewCreditCardDetailsPage implements OnInit {

  ccId: string | null;
  ccToView: CreditCard | null;

  currentUser: User | null;
  hasLoaded: boolean;

  displayConfirmDeleteButton: boolean;
  successfulDeletion: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionService,
    private creditCardService: CreditCardService) { }

  ngOnInit() {

    this.ccId = this.activatedRoute.snapshot.paramMap.get('ccId');
    this.currentUser = this.sessionService.getCurrentUser();
    this.displayConfirmDeleteButton = false;
    this.successfulDeletion = false;

    if(this.ccId != null)
    {
      this.hasLoaded = true;
      console.log('View credit card: ' + this.ccId);

      //Implement bridge with try catch
      // eslint-disable-next-line radix
      this.ccToView = this.creditCardService.getCreditCardById(parseInt(this.ccId));

    }
  }

  formatCreditCardNumber() {
    const stringNum = this.ccToView.ccNumber.toString();
    return stringNum.substring(0,4) + ' ' + stringNum.substring(4,8) +
    ' ' + stringNum.substring(8,12) + ' ' + stringNum.substring(12,16);
  }

  formatCreditCardExpiryDate() {
    const stringDate = this.ccToView.expiryDate.getMonth() + 1 +
    '/' + this.ccToView.expiryDate.getFullYear();
    return stringDate;
  }

  deleteCreditCard() {
    console.log('Deleting credit card..');
    this.successfulDeletion = true;
  }

  toggleConfirmDeleteButton() {
    console.log('Toggling delete button');
    //To implement
    this.displayConfirmDeleteButton = true;
  }


}
