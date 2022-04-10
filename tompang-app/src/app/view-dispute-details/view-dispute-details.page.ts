import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { DisputeService } from '../services/dispute.service';
import { Dispute } from '../models/dispute';

@Component({
  selector: 'app-view-dispute-details',
  templateUrl: './view-dispute-details.page.html',
  styleUrls: ['./view-dispute-details.page.scss'],
})
export class ViewDisputeDetailsPage implements OnInit {

  disputeId: string;
  disputeToView: Dispute;

  hasLoaded: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private disputeService: DisputeService) { }

  ngOnInit() {
    console.log('View dispute details page');
    this.disputeId = this.activatedRoute.snapshot.paramMap.get('disputeId');

    if (this.disputeId !== null) {
      // eslint-disable-next-line radix
      this.disputeService.getDisputeById(parseInt(this.disputeId)).subscribe({
        next: (response) => {
          this.disputeToView = response;
          console.log(this.disputeToView);
          this.hasLoaded = true;
          console.log('Found Dispute To View');
        },
        error: (error) => {
          console.log('viewDispute.ts:' + error);
        },
      });
    }

  }

  viewTransaction() {
    console.log('View transaction details from view dispute details page');
    this.router.navigate(['/view-transaction-details/' + this.disputeToView.transaction.transactionId]);

  }


}
