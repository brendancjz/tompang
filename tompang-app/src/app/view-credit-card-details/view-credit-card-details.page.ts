import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditCard } from '../models/creditCard';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';
import { AlertController } from '@ionic/angular';
import { UserService } from '../services/user.service';
import { Location } from '@angular/common';

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

  error: boolean;
  errorMessage: string;


  constructor(private location: Location, private router: Router,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionService,
    public alertController: AlertController,
    private userService: UserService) { }

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewCreditCardDetails');
    this.ccId = this.activatedRoute.snapshot.paramMap.get('ccId');
    this.currentUser = this.sessionService.getCurrentUser();
    this.displayConfirmDeleteButton = false;
    this.successfulDeletion = false;

    if(this.ccId != null)
    {
      console.log('View credit card: ' + this.ccId);

      // eslint-disable-next-line radix
      this.userService.getCreditCardById(Number(this.ccId)).subscribe({
        next: (response) => {
          this.ccToView = response;
          this.hasLoaded = true;
          console.log(this.ccToView);
        },
        error: (error) => {
          this.error = true;
          console.log('********** View Credit Card Details.ts: ' + error);
          this.location.back();
        },
      });

    }
  }

  doDeleteCreditCard() {
    console.log('Deleting credit card..');

    // eslint-disable-next-line radix
    this.userService.deleteCreditCard(parseInt(this.ccId)).subscribe({
      next:(response)=>{
        this.successfulDeletion = true;

      },
      error:(error)=>{
        this.error = true;
        this.errorMessage = error;
      }
    });
  }

  formatCreditCardNumber() {
    const stringNum = this.ccToView.ccNumber.toString();
    return stringNum.substring(0,4) + ' ' + stringNum.substring(4,8) +
    ' ' + stringNum.substring(8,12) + ' ' + stringNum.substring(12,16);
  }

  formatCreditCardExpiryDate() {
    const expiryDate = new Date(Number(this.ccToView.expiryDate.toString().substring(0,4)),
    Number(this.ccToView.expiryDate.toString().substring(5,7)));
    const stringDate = expiryDate.getMonth() + 1 +
    '/' + expiryDate.getFullYear();

    return stringDate;
  }

  toggleConfirmDeleteButton() {
    console.log('Toggling delete button');
    this.displayConfirmDeleteButton = true;
  }


}
