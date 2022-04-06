import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-transaction-details',
  templateUrl: './view-transaction-details.page.html',
  styleUrls: ['./view-transaction-details.page.scss'],
})
export class ViewTransactionDetailsPage implements OnInit {

  transactionId: string | undefined;
  transactionToView: Transaction | undefined;
  retrieveTransationError: boolean;

  hasLoaded: boolean;
  currentUser: User;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,) { }

  ngOnInit() {
    this.transactionId = this.activatedRoute.snapshot.paramMap.get('transactionId');
    this.currentUser = this.sessionService.getCurrentUser();

    if (this.transactionId !== null) {
      this.hasLoaded = true;
      this.transactionToView = new Transaction(2, 100);
      //To implement
    }
  }

  raiseDispute(): void {
    console.log('Raising dispute...');
  }

}
