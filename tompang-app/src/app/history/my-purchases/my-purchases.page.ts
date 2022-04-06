import { Component, OnInit } from '@angular/core';
import { sample } from 'rxjs/operators';
import { Transaction } from 'src/app/models/transaction';
import { ListingService } from 'src/app/services/listing.service';

@Component({
  selector: 'app-my-purchases',
  templateUrl: './my-purchases.page.html',
  styleUrls: ['./my-purchases.page.scss'],
})
export class MyPurchasesPage implements OnInit {

  transactions: Transaction[];

  constructor(private listingService: ListingService) { }

  ngOnInit() {
    this.transactions = [];
    let sampleTransaction = new Transaction(2, 25.25);
    sampleTransaction.listing = this.listingService.getSampleListing();
    sampleTransaction.isAccepted = true;
    this.transactions.push(sampleTransaction);

    sampleTransaction = new Transaction(3, 30);
    sampleTransaction.listing = this.listingService.getSampleListing();
    sampleTransaction.hasDispute = true;
    this.transactions.push(sampleTransaction);

    sampleTransaction = new Transaction(4, 35);
    sampleTransaction.listing = this.listingService.getSampleListing();
    sampleTransaction.isCompleted = true;
    this.transactions.push(sampleTransaction);

    sampleTransaction = new Transaction(5, 310);
    sampleTransaction.listing = this.listingService.getSampleListing();
    sampleTransaction.isRejected = true;
    this.transactions.push(sampleTransaction);

    this.transactions.push(sampleTransaction, sampleTransaction, sampleTransaction);
  }

  viewTransaction(transaction: Transaction) {
    console.log('view transaction details');
  }

  formatListingTitle(transaction: Transaction): string {
    const maxNumberBeforeCutOff = 20;
    if (transaction.listing.title.length >= maxNumberBeforeCutOff) {
      return transaction.listing.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return transaction.listing.title;
  }

  formatListingETA(transaction: Transaction): string {
    const etaString = transaction.listing.expectedArrivalDate.toString();

    return etaString.substring(4,15);
  }

}
