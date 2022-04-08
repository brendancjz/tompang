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

  disputeId: number
  dispute: Dispute

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private disputeService: DisputeService) { }

  ngOnInit() {
    this.disputeId = Number(
      this.activatedRoute.snapshot.paramMap.get('transactionId')
    );

    this.disputeService.getDisputeById(this.disputeId).subscribe({
      next: (response) => {
        this.dispute = response;
        console.log('Found Dispute To View');
      },
      error: (error) => {
        console.log('viewDispute.ts:' + error);
      },
    });
  }



}
