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

  disputeDescription: string | undefined;

  raisingDispute: boolean;

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
    this.raisingDispute = false;

    this.transactionService.getTransactionById(this.transactionId).subscribe({
      next: (response) => {
        this.transactionToView = response;
        console.log('Found Transaction To View');
        console.log(this.transactionToView);
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

    this.doValidation();
    if (this.resultError) {
      return;
    }

    const newDispute = new Dispute();
    newDispute.description = this.disputeDescription;

    this.disputeService
      .createDispute(this.transactionId, newDispute)
      .subscribe({
        next: (response) => {
          const newDisputeId: number = response;
          this.resultSuccess = true;
          this.resultError = false;
          this.message = 'Sucessfully raise a dispute';
          console.log('Dispute ' + newDisputeId + ' raised successfully');

          this.resetPage();
        },
        error: (error) => {
          console.log('raiseDispute.ts:' + error);
          this.resultSuccess = false;
          this.resultError = true;
          this.message = 'Invalid entry: Unexpected error occured. Try again later';
        },
      });
  }

  doValidation() {
    if (this.disputeDescription === undefined) {
      this.resultError = true;
      this.message = 'Invalid entry: Missing input field';
      return;
    }
  }

  resetPage() {
    this.disputeDescription = undefined;
  }

  toggleRaiseDispute() {
    this.raisingDispute = true;
  }
}
