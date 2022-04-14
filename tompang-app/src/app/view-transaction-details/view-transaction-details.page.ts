import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';
import { TransactionService } from '../services/transaction.service';
import { Dispute } from '../models/dispute';
import { DisputeService } from '../services/dispute.service';
import {
  BarcodeScanner,
  BarcodeScannerOptions,
} from '@ionic-native/barcode-scanner/ngx';
import { Location } from '@angular/common';

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
  isSeller: boolean | null;

  showQrCode: boolean | null;
  scannedQRCode = {};
  encodedData: any;
  // eslint-disable-next-line @typescript-eslint/naming-convention
  QRScannerOptions: BarcodeScannerOptions;

  constructor(
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private transactionService: TransactionService,
    private disputeService: DisputeService,
    private scanner: BarcodeScanner
  ) {
    this.QRScannerOptions = {
      showTorchButton: true,
      showFlipCameraButton: true,
    };
  }

  ngOnInit() {}

  ionViewWillEnter() {
    this.showQrCode = false;
    console.log(this.showQrCode);
    console.log('ionViewWillEnter for ViewTransactionDetails');
    this.hasLoaded = false;
    this.transactionId = Number(
      this.activatedRoute.snapshot.paramMap.get('transactionId')
    );
    this.currentUser = this.sessionService.getCurrentUser();
    this.raisingDispute = false;

    this.transactionService.getTransactionById(this.transactionId).subscribe({
      next: (response) => {
        this.transactionToView = response;
        console.log('TransactionToView: ', this.transactionToView);

        if (this.transactionToView.seller.userId === this.currentUser.userId) {
          this.isSeller = true;
        } else {
          this.isSeller = false;
        }
      },
      error: (error) => {
        console.log('viewTransaction.ts:' + error);
        this.location.back();
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
          this.message =
            'Invalid entry: Unexpected error occured. Try again later';
        },
      });
  }

  showQRCode() {
    this.showQrCode = !this.showQrCode;
  }

  scanQRCode() {
    this.scanner
      .scan()
      .then((res) => {
        console.log('confirm-transaction/' + res.text);
        this.router.navigate(['/confirm-transaction/' + res.text]);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  generateQRCode() {
    this.scanner.encode(this.scanner.Encode.TEXT_TYPE, this.transactionId).then(
      (res) => {
        console.log('generated QR: ' + res);
        this.router.navigate(['/view-conversation/' + 1]);
        // this.router.navigate(['/view-listing-details/' + .listingId]);
        this.encodedData = res;

        this.transactionService
          .completeTransaction(this.transactionId)
          .subscribe({
            next: (response) => {},
          });
      },
      (error) => {
        console.log(error);
      }
    );
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

  viewListing(): void {
    console.log('View listing details from view transaction page');
    this.router.navigate([
      '/view-listing-details/' + this.transactionToView.listing.listingId,
    ]);
  }
}
