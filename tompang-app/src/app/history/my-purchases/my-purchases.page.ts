import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { sample } from 'rxjs/operators';
import { Transaction } from 'src/app/models/transaction';
import { ListingService } from 'src/app/services/listing.service';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-my-purchases',
  templateUrl: './my-purchases.page.html',
  styleUrls: ['./my-purchases.page.scss'],
})
export class MyPurchasesPage implements OnInit {
  transactions: Transaction[];
  transaction: Transaction;

  constructor(
    private router: Router,
    private listingService: ListingService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.transactions = [];
    this.transactionService.getUserTransactions().subscribe({
      next: (response) => {
        this.transactions = response;
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
}
