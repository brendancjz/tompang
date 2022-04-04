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

  ccBrand: string | null;
  ccNumber: number | null;
  ccName: string | null;
  ccCIV: number | null;
  expiryYear: number | null;
  expiryMonth: number | null;
  years: number[];
  months: number[];

  hasCreationCCError: boolean;
  errorMsg: string;
  successfulCreation: boolean;

  constructor(private location: Location,
    public sessionService: SessionService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
    this.isDisplayingCCList = true;
    this.years = this.initialiseYears();
    this.months = this.initialiseMonths();
    this.hasCreationCCError = false;
  }
  resetPage() {
    return;
  }

  viewCreditCard(creditcard: CreditCard) {
    console.log('Viewing credit card..');
  }

  addCreditCard() {
    console.log('Creating new credit card..');
    this.doValidation();
    //TODO
    const newCC = new CreditCard(2, this.ccBrand, this.ccName, this.ccNumber, this.ccCIV, new Date());
    console.log(newCC);

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

    if (this.ccBrand === undefined ||
      this.ccName === undefined ||
      this.ccNumber === undefined ||
      this.ccCIV === undefined ||
      this.expiryMonth === undefined ||
      this.expiryYear === undefined) {
        this.hasCreationCCError = true;
        this.errorMsg = 'Sorry, Missing and invalid inputs.';
        return;
      }

    if (this.ccNumber.toString().length !== 16) {
      this.hasCreationCCError = true;
      this.errorMsg = 'Sorry, Credit Card Number is not correctly formatted.';
    }

    if (this.ccBrand !== 'DBS' && this.ccBrand !== 'AMEX' && this.ccBrand !== 'SC' &&
    this.ccBrand !== 'MasterCard' && this.ccBrand !== 'OCBC' && this.ccBrand !== 'UOB') {
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
    return stringNum.substring(0,4) + ' ' + stringNum.substring(4,8) +
    ' ' + stringNum.substring(8,12) + ' ' + stringNum.substring(12,16);
  }

  initialiseYears() {
    return [1,2,3,4,5,6,7,8,9,10,
            11,12,13,14,15,16,17,18,19,20,
            21,22,23,24,25,26,27,28,29,30,
            31,32,33,34,35,36,37,38,39,40,
            41,42,43,44,45,46,47,48,49,50,
            51,52,53,54,55,56,57,58,59,60,
            61,62,63,64,65,66,67,68,69,70,
            71,72,73,74,75,76,77,78,79,80,
            81,82,83,84,85,86,87,88,89,90,
            91,92,93,94,95,96,97,98,99,];
  }

  initialiseMonths() {
    return [1,2,3,4,5,6,7,8,9,10,
      11,12,13,14,15,16,17,18,19,20,
      21,22,23,24,25,26,27,28,29,30,31];
  }
}
