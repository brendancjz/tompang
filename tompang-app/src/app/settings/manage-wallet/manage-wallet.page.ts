import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';
import { CreditCard } from 'src/app/models/creditCard';
import { User } from 'src/app/models/user';


@Component({
  selector: 'app-manage-wallet',
  templateUrl: './manage-wallet.page.html',
  styleUrls: ['./manage-wallet.page.scss'],
})
export class ManageWalletPage implements OnInit {

  creditCards: CreditCard[];
  currentUser: User;
  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  creditCard: CreditCard;
  hasSelectedCard: boolean;

  constructor(public sessionService: SessionService,
    public userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.sessionService.getCurrentUser();
    console.log('Current User', this.currentUser);
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter ManageCreditCards');
    this.currentUser = this.sessionService.getCurrentUser();
    console.log('Current User', this.currentUser);

    this.userService.getUserCreditCards().subscribe({
      next: (response) => {
        this.creditCards = response;

      },
      error: (error) => {
        console.log('getCreditCards.ts:' + error);
      },
    });
  }

  withdrawFunds(){

    this.doValidation();
    if (!this.hasSelectedCard) {
      return;
    }

    const updatedUser = new User(
      this.currentUser.userId,
      this.currentUser.firstName,
      this.currentUser.lastName,
      this.currentUser.username,
      this.currentUser.password,
      this.currentUser.email,
      this.currentUser.dateOfBirth,
      this.currentUser.profilePic,
      this.currentUser.contactNumber,
    );

    updatedUser.walletAmount = 0;

    this.userService.updateUser(updatedUser).subscribe({
      next: (response) => {
        console.log('Update user success!');
        this.resultSuccess = true;
        this.resultError = false;
        this.message = 'Wallet funds withdrawn successfully';
        this.currentUser.walletAmount = 0;
        this.sessionService.setCurrentUser(this.currentUser);
        console.log('getCurrentUser', this.sessionService.getCurrentUser());

      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'Invalid edit: Unexpected error occured. Try again later.';

        console.log('********** UpdateUserPage: ' + error);
      },
    });

  }

  formatCreditCardNumber(ccNum: number) {
    const stringNum = ccNum.toString();
    return stringNum.substring(0, 4) + ' ' + stringNum.substring(4, 8) +
      ' ' + stringNum.substring(8, 12) + ' ' + stringNum.substring(12, 16);
  }

  formatCreditCardInfo(card: CreditCard): string {
    return card.ccBrand + ' - ' + this.formatCreditCardNumber(card.ccNumber);
  }

  doValidation() {
    this.hasSelectedCard = true;
    if (this.creditCard === undefined) {
      this.message = 'Please select a credit card.';
      this.hasSelectedCard = false;
      return;
    }

    if (this.currentUser.walletAmount === 0) {
      this.message = 'Nothing to withdraw. Complete a transaction first.';
      this.hasSelectedCard = false;
      return;
    }
  }

}
