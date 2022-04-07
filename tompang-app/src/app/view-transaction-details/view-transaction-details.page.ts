import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';
import { TransactionService } from '../services/transaction.service';
import { Dispute } from '../models/dispute';
import { DisputeService } from '../services/dispute.service';

@Component({
  selector: 'app-view-transaction-details',
  templateUrl: './view-transaction-details.page.html',
  styleUrls: ['./view-transaction-details.page.scss'],
})
export class ViewTransactionDetailsPage implements OnInit {
  transactionId: number | undefined;
  transactionToView: Transaction | undefined;
  retrieveTransationError: boolean;
  dispute: Dispute | undefined;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  hasLoaded: boolean;
  currentUser: User;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private transactionService: TransactionService,
    private disputeService: DisputeService
  ) {}

  ngOnInit() {
    this.transactionId = Number(
      this.activatedRoute.snapshot.paramMap.get('transactionId')
    );
    this.currentUser = this.sessionService.getCurrentUser();
    this.dispute = new Dispute();

    this.transactionService.getTransactionById(this.transactionId).subscribe({
      next: (response) => {
        this.transactionToView = response;
        console.log('success');
      },
      error: (error) => {
        console.log('viewTransaction.ts:' + error);
      },
    });

    if (this.transactionId !== null) {
      this.hasLoaded = true;
      this.transactionToView = new Transaction(2, 100);
      //To implement
    }
  }

  raiseDispute(): void {
    this.disputeService
      .createDispute(this.transactionId, this.dispute)
      .subscribe({
        next: (response) => {
          let newDisputeId: number = response;
          this.resultSuccess = true;
          this.resultError = false;
          this.message = 'Dispute ' + newDisputeId + ' raised successfully';
        },
        error: (error) => {
          console.log('raiseDispute.ts:' + error);
        },
      });
  }
}
