import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';
import { CreditCardService } from 'src/app/services/creditCard.service';
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

  constructor(public sessionService: SessionService,
    public userService: UserService,
    private creditCardService: CreditCardService) { }

  ngOnInit() {
    this.currentUser = this.sessionService.getCurrentUser();
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter ManageCreditCards');
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

      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'Invalid edit: Unexpected error occured. Try again later.';

        console.log('********** UpdateUserPage: ' + error);
      },
    });

  }

}
