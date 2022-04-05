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

  hasCreationCCError: boolean;
  errorMsg: string;
  successfulCreation: boolean;

  constructor(
    private router: Router,
    private location: Location,
    public sessionService: SessionService,
    public userService: UserService
  ) {}

  ngOnInit() {
    document.getElementById('back-button').addEventListener(
      'click',
      () => {
        this.resetPage();
      },
      { once: true }
    );

    this.currentUser = this.sessionService.getCurrentUser();
    this.isDisplayingCCList = true;
    this.years = this.initialiseYears();
    this.months = this.initialiseMonths();
    this.hasCreationCCError = false;

    this.userService.getUserCreditCards().subscribe({
      next: (response) => {
        this.creditCards = response;
      },
      error: (error) => {
        console.log('getAllAvailableListings.ts:' + error);
      },
    });
  }
  resetPage() {
    return;
  }

  viewCreditCard(creditcard: CreditCard) {
    console.log('Viewing credit card..');
    this.router.navigate(['/view-credit-card-details/' + creditcard.ccId]);
  }

  addCreditCard() {
    console.log('Creating new credit card..');
    this.doValidation();
    if (this.hasCreationCCError) {
      console.log('has creation of credit card error');
      return;
    }
    //TODO
    let creditCard: CreditCard = new CreditCard();
    creditCard.ccBrand = this.ccBrand;
    console.log(this.ccBrand);
    creditCard.ccName = this.ccName;
    console.log(this.ccName);
    creditCard.ccNumber = this.ccNumber;
    console.log(this.ccNumber);
    creditCard.ccCIV =  this.ccCIV;
    console.log(this.ccCIV);
    creditCard.expiryDate = new Date(this.expiryYear, this.expiryMonth)
    console.log(this.expiryYear);
    console.log(this.expiryMonth);
    

    this.userService.createCreditCard(creditCard).subscribe({
      next:(response)=>{
        let newCreditCardId: number = response;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "New Credit Card " + newCreditCardId + " created successfully";
      },
      error:(error)=>{
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while creating the new credit card: " + error;

        console.log('********** CreateNewCreditCardPage: ' + error);
      }
    });

    this.ccBrand = null;
    this.ccName = null;
    this.ccNumber = null;
    this.ccCIV = null;
    this.expiryMonth = null;
    this.expiryYear = null;
    this.successfulCreation = true;
  }

  doValidation() {
    this.hasCreationCCError = false;

    if (
      this.ccBrand === undefined ||
      this.ccName === undefined ||
      this.ccNumber === undefined ||
      this.ccCIV === undefined ||
      this.expiryMonth === undefined ||
      this.expiryYear === undefined
    ) {
      this.hasCreationCCError = true;
      this.errorMsg = 'Sorry, Missing and invalid inputs.';
      return;
    }

    if (this.ccNumber.toString().length !== 16) {
      this.hasCreationCCError = true;
      this.errorMsg = 'Sorry, Credit Card Number is not correctly formatted.';
    }

    if (
      this.ccBrand !== 'DBS' &&
      this.ccBrand !== 'AMEX' &&
      this.ccBrand !== 'SC' &&
      this.ccBrand !== 'MasterCard' &&
      this.ccBrand !== 'OCBC' &&
      this.ccBrand !== 'UOB'
    ) {
      this.hasCreationCCError = true;
      this.errorMsg = 'Sorry, invalid Bank input.';
    }

    if (this.ccCIV.toString().length !== 3) {
      this.hasCreationCCError = true;
      this.errorMsg = 'Sorry, Credit Card CIV is not correctly formatted.';
    }
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
