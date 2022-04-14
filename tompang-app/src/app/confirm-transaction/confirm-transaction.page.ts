import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Listing } from '../models/listing';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';
import { TransactionService } from '../services/transaction.service';

@Component({
  selector: 'app-confirm-transaction',
  templateUrl: './confirm-transaction.page.html',
  styleUrls: ['./confirm-transaction.page.scss'],
})
export class ConfirmTransactionPage implements OnInit {
  transactionToView: Transaction | undefined;
  transactionListingTitle: string | undefined;
  transactionListingQuantity: number | undefined;
  currentUser: User | undefined;
  transactionId: number | undefined;

  hasLoaded: boolean;

  buyer: string | undefined;
  seller: string | undefined;

  confirmSuccess: boolean;

  constructor(
    private location: Location,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.hasLoaded = false;
    this.confirmSuccess = false;
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter ConfirmTransaction');

    this.transactionId = Number(
      this.activatedRoute.snapshot.paramMap.get('transactionId')
    );

    this.currentUser = this.sessionService.getCurrentUser();

    this.transactionService.getTransactionById(this.transactionId).subscribe({
      next: (response) => {
        this.transactionToView = response;
        console.log('TransactToView', this.transactionToView);
        this.hasLoaded = true;

        this.buyer = response.buyer.username;
        this.seller = response.seller.username;
      },
      error: (error) => {
        console.log('viewTransaction.ts:' + error);
        this.location.back();
      },
    });
  }

  doCompleteTransaction() {
    this.transactionService.completeTransaction(this.transactionId).subscribe({
      next: (response) => {
        console.log('Transaction confirmed.');
        this.confirmSuccess = true;
      }, error: (error) => {
        console.log('completeTransaction.ts:' + error);
      },
    });
  }
}
