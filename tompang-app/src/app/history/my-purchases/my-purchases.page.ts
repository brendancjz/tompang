import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { sample } from 'rxjs/operators';
import { Transaction } from 'src/app/models/transaction';
import { ListingService } from 'src/app/services/listing.service';
import { TransactionService } from 'src/app/services/transaction.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-my-purchases',
  templateUrl: './my-purchases.page.html',
  styleUrls: ['./my-purchases.page.scss'],
})
export class MyPurchasesPage implements OnInit {
  transactions: Transaction[];
  transaction: Transaction;
  userId: number;

  totalAmountSpent: number | undefined;
  totalAmountEarned: number | undefined;

  constructor(
    private router: Router,
    private listingService: ListingService,
    private transactionService: TransactionService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.transactions = [];
    this.totalAmountEarned = 0;
    this.totalAmountSpent = 0;

  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter MyPurchases');
    this.totalAmountEarned = 0;
    this.totalAmountSpent = 0;
    this.userId = this.sessionService.getCurrentUser().userId;

    this.transactionService.getUserTransactions().subscribe({
      next: (response) => {
        this.transactions = response;

        // eslint-disable-next-line @typescript-eslint/prefer-for-of
        for (let i = 0; i < this.transactions.length; i++) {
          const transaction = this.transactions[i];
          if (transaction.buyer.userId === this.userId && transaction.isCompleted) {
            console.log('Bought', transaction);
            this.totalAmountSpent += transaction.amount;
          } else if (transaction.buyer.userId !== this.userId && transaction.isCompleted) {
            console.log('Sold', transaction);
            this.totalAmountEarned += transaction.listing.price * 0.97;
          }
        }
      },
      error: (error) => {
        console.log('getAllUserTransactions.ts:' + error);
      },
    });
  }

  viewTransaction(transactionId: number)  {
    console.log('view transaction details');

    this.router.navigate([
      '/view-transaction-details/' + transactionId,
    ]);
  }

  formatListingTitle(transaction: Transaction): string {
    const maxNumberBeforeCutOff = 20;
    if (transaction.listing.title.length >= maxNumberBeforeCutOff) {
      return (
        transaction.listing.title.substring(0, maxNumberBeforeCutOff - 3) +
        '...'
      );
    }

    return transaction.listing.title;
  }

  formatListingETA(transaction: Transaction): string {
    const etaString = transaction.listing.expectedArrivalDate.toString().split('T')[0];

    return etaString;
  }

  checkTransaction(transaction: Transaction): string{

    if (transaction.buyer.userId === this.userId) {
        return 'Purchased';
    } else {
      return 'Sold';
    }
  }
}
