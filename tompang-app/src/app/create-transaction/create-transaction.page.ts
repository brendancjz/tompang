import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditCard } from '../models/creditCard';
import { Listing } from '../models/listing';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { TransactionService } from '../services/transaction.service';

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.page.html',
  styleUrls: ['./create-transaction.page.scss'],
})
export class CreateTransactionPage implements OnInit {

  listingId: string | null;
  listingToView: Listing | null;
  retrieveListingError: boolean;
  eta: string | null;

  hasLoaded: boolean;

  currentUser: User | null;
  transaction: Transaction

  buyerCreditCard: CreditCard;
  transactionSuccessful: boolean;
  resultError: boolean
  message: String

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService,
    private transactionService: TransactionService) { }

  ngOnInit() {

    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');
    this.currentUser = this.sessionService.getCurrentUser();
    this.transaction = new Transaction();
    //ToChange
    this.listingService.getAllAvailableListings().subscribe({
      next:(response)=> {
        this.listingToView = response[9];
        this.eta = this.listingToView.expectedArrivalDate.toString().split('T')[0];
        this.hasLoaded = true;
      },
      error:(error)=>{
        this.retrieveListingError = true;
        console.log('********** View Listing Details Page.ts: ' + error);
      }
    });
  }

  viewListing(): void {
    this.router.navigate(['/view-listing-details/' + this.listingId]);
  }

  getListingPicUrl(): string {
    const basePicUrl = '../../assets/images';

    return basePicUrl + this.listingToView.photos[0];
  }

  createTransaction() {
    console.log('Creating transaction..');
    this.transaction.seller = this.listingToView.createdBy;
    this.transaction.createdOn = new Date();
    this.transaction.amount = this.listingToView.price;

    this.transactionService.createTransaction(Number(this.listingId), this.transaction).subscribe({
      next:(response)=>{
        console.log('Successful creation of transaction');
        const newTransactionId: number = response;
        this.transactionSuccessful = true;
        this.resultError = false;
        this.message = 'Successfully requested to purchase listing';       
      },
      error:(error)=>{
        this.resultError = true;
        this.transactionSuccessful= false;
        this.message = 'Invalid Creation: Unexpected error occured. Try again later.';

        console.log('********** CreateNewTransactionPage: ' + error);
      }
    });
    
    if (this.buyerCreditCard !== undefined) {
      this.transactionSuccessful = true;
    } else {
      this.transactionSuccessful = false;
    }

  }

  formatCreditCardNumber(ccNum: number) {
    const stringNum = ccNum.toString();
    return stringNum.substring(0,4) + ' ' + stringNum.substring(4,8) +
    ' ' + stringNum.substring(8,12) + ' ' + stringNum.substring(12,16);
  }

  formatCreditCardInfo(card: CreditCard): string {
    return card.ccBrand + ' - ' + this.formatCreditCardNumber(card.ccNumber);
  }

}
