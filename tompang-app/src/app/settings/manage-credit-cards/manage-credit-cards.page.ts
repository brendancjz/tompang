import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/models/user';
import { CreditCard } from 'src/app/models/creditCard';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-manage-credit-cards',
  templateUrl: './manage-credit-cards.page.html',
  styleUrls: ['./manage-credit-cards.page.scss'],
})
export class ManageCreditCardsPage implements OnInit {
  currentUser: User;

  crediCardToDelete: CreditCard;
  isDisplayingCCList: boolean;
  creditCards: CreditCard[];

  ccBrand: string | null;
  ccNumber: number | null;
  ccName: string | null;
  ccCIV: number | null;
  expiryYear: number | null;
  expiryMonth: number | null;
  years: number[];
  months: number[];

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(
    private router: Router,
    private location: Location,
    public sessionService: SessionService,
    public userService: UserService,
  ) {}

  ngOnInit() {
    console.log('Manage Credit Cards Page OnInIt');
    document.getElementById('back-button').addEventListener('click',() => {
        this.resetPage();
      },
      { once: true }
    );

    this.currentUser = this.sessionService.getCurrentUser();
    this.isDisplayingCCList = true;
    this.years = this.initialiseYears();
    this.months = this.initialiseMonths();
    this.resultError = false;
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter ManageCreditCards');
    this.userService.getUserCreditCards().subscribe({
      next: (response) => {
        this.creditCards = response;
        this.resultSuccess = false;
      },
      error: (error) => {
        console.log('getAllAvailableListings.ts:' + error);
      },
    });
  }

  addCreditCard() {
    console.log('Creating new credit card..');
    this.doValidation();
    if (this.resultError) {
      console.log('Validation error for credit card');
      return;
    }

    const creditCard: CreditCard = new CreditCard();
    creditCard.ccBrand = this.ccBrand;
    creditCard.ccName = this.ccName;
    creditCard.ccNumber = this.ccNumber;
    creditCard.ccCIV =  this.ccCIV;
    creditCard.expiryDate = new Date(this.expiryYear, this.expiryMonth);


    this.userService.createCreditCard(creditCard).subscribe({
      next:(response)=>{
        console.log('Successful creation of credit card');
        const newCreditCardId: number = response;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = 'Successfully added a new Credit Card!';

        creditCard.ccId = newCreditCardId;
        this.creditCards.push(creditCard);
        this.resetPage();
      },
      error:(error)=>{
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'Invalid Creation: Unexpected error occured. Try again later.';

        console.log('********** CreateNewCreditCardPage: ' + error);
      }
    });

  }

  doValidation() {
    this.resultError = false;

    if (
      this.ccBrand === undefined ||
      this.ccName === undefined ||
      this.ccNumber === undefined ||
      this.ccCIV === undefined ||
      this.expiryMonth === undefined ||
      this.expiryYear === undefined
    ) {
      this.resultError = true;
      this.message = 'Sorry, Missing and invalid inputs.';
      return;
    }

    if (this.ccNumber.toString().length !== 16) {
      this.resultError = true;
      this.message = 'Sorry, Credit Card Number is not correctly formatted.';
    }

    if (
      this.ccBrand !== 'DBS' &&
      this.ccBrand !== 'AMEX' &&
      this.ccBrand !== 'SC' &&
      this.ccBrand !== 'MasterCard' &&
      this.ccBrand !== 'OCBC' &&
      this.ccBrand !== 'UOB'
    ) {
      this.resultError = true;
      this.message = 'Sorry, invalid Bank input.';
    }

    if (this.ccCIV.toString().length !== 3) {
      this.resultError = true;
      this.message = 'Sorry, Credit Card CIV is not correctly formatted.';
    }
  }

  resetPage() {
    this.ccBrand = null;
    this.ccName = null;
    this.ccNumber = null;
    this.ccCIV = null;
    this.expiryMonth = null;
    this.expiryYear = null;
    this.isDisplayingCCList = true;
  }

  viewCreditCard(creditcard: CreditCard) {
    console.log('Viewing credit card..');
    this.router.navigate(['/view-credit-card-details/' + creditcard.ccId]);
  }
  toggleAddCreditCardPage() {
    console.log('Adding credit card page..');
    this.isDisplayingCCList = !this.isDisplayingCCList;
  }

  formatCreditCardNumber(ccNum: number) {
    const stringNum = ccNum.toString();
    return (
      stringNum.substring(0, 4) +
      ' ' +
      stringNum.substring(4, 8) +
      ' ' +
      stringNum.substring(8, 12) +
      ' ' +
      stringNum.substring(12, 16)
    );
  }

  initialiseYears() {
    return [
      2022, 2023, 2024, 2025, 2026, 2027, 2028,2029,2030
    ];
  }

  initialiseMonths() {
    return [
      0,1,2,3,4,5,6,7,8,9,10,11
    ];
  }
}
