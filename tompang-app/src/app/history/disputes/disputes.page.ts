import { Component, OnInit } from '@angular/core';
import { DisputeService } from 'src/app/services/dispute.service';
import { Dispute } from 'src/app/models/dispute';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-disputes',
  templateUrl: './disputes.page.html',
  styleUrls: ['./disputes.page.scss'],
})
export class DisputesPage implements OnInit {
  disputes: Dispute[];

  constructor(private router: Router,
    private disputeService: DisputeService,
    private sessionService: SessionService) { }

  ngOnInit() {
    this.disputes = [];
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter Disputes');

    this.disputeService.getUserDisputes().subscribe({
      next: (response) => {
        this.disputes = response;
        console.log('Disputes', this.disputes);
      },
      error: (error) => {
        console.log('getAllUserTransactions.ts:' + error);
      },
    });
  }

  viewDispute(disputeId: number){
    this.router.navigate([
      '/view-dispute-details/' + disputeId,
    ]);
  }

  checkDispute(dispute: Dispute): string{

    if (dispute.transaction.buyer.userId === dispute.userId) {
        return 'Purchased on ';
    } else {
      return 'Sold on ';
    }
  }

  formatListingTitle(dispute: Dispute) {
    const maxNumberBeforeCutOff = 25;
    if (dispute.transaction.listing.title.length >= maxNumberBeforeCutOff) {
      return dispute.transaction.listing.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return dispute.transaction.listing.title;
  }

  formatTransactionDate(dispute: Dispute) {
    const dateString = dispute.transaction.createdOn.toString().split('T')[0];

    return dateString;
  }
}
