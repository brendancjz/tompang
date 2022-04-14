import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { DisputeService } from '../services/dispute.service';
import { Dispute } from '../models/dispute';
import { Location } from '@angular/common';

@Component({
  selector: 'app-view-dispute-details',
  templateUrl: './view-dispute-details.page.html',
  styleUrls: ['./view-dispute-details.page.scss'],
})
export class ViewDisputeDetailsPage implements OnInit {
  disputeId: string;
  disputeToView: Dispute;

  hasLoaded: boolean;

  constructor(
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private disputeService: DisputeService
  ) {}

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewDisputeDetails');

    this.disputeId = this.activatedRoute.snapshot.paramMap.get('disputeId');

    if (this.disputeId !== null) {
      // eslint-disable-next-line radix
      this.disputeService.getDisputeById(parseInt(this.disputeId)).subscribe({
        next: (response) => {
          this.disputeToView = response;
          this.hasLoaded = true;
          console.log('DisputeToView', this.disputeToView);
        },
        error: (error) => {
          console.log('viewDispute.ts:' + error);
          this.location.back();
        },
      });
    }
  }

  doViewTransactionDetails() {
    console.log('View transaction details from view dispute details page');
    console.log(this.disputeToView.transaction);
    this.router.navigate([
      '/view-transaction-details/' +
        this.disputeToView.transaction.transactionId,
    ]);
  }
}
