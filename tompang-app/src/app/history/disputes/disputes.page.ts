import { Component, OnInit } from '@angular/core';
import { DisputeService } from 'src/app/services/dispute.service';
import { Dispute } from 'src/app/models/dispute';
import { Router } from '@angular/router';

@Component({
  selector: 'app-disputes',
  templateUrl: './disputes.page.html',
  styleUrls: ['./disputes.page.scss'],
})
export class DisputesPage implements OnInit {
  disputes: Dispute[];

  constructor(private router: Router,
    private disputeService: DisputeService) { }

  ngOnInit() {
    this.disputes = [];

    this.disputeService.getUserDisputes().subscribe({
      next: (response) => {
        this.disputes = response;
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

}
