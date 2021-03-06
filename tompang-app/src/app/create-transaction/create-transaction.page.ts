import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditCard } from '../models/creditCard';
import { Listing } from '../models/listing';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { Message } from '../models/message';
import { Conversation } from '../models/conversation';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { TransactionService } from '../services/transaction.service';
import { Location } from '@angular/common';

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
  transaction: Transaction;

  buyerCreditCard: CreditCard;
  transactionSuccessful: boolean;
  result = 0;
  resultError: boolean;
  message: string;
  resultSuccess: boolean;



  hasUserAlreadyBoughtThis: boolean;

  quantities: number[];


  constructor(private location: Location, private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService,
    private transactionService: TransactionService) { }

  ngOnInit() {

    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');
    this.currentUser = this.sessionService.getCurrentUser();
    this.transaction = new Transaction();
    this.hasUserAlreadyBoughtThis = false;
    this.quantities = [];

    // eslint-disable-next-line radix
    this.listingService.getListingByListingId(parseInt(this.listingId)).subscribe({
      next: (response) => {
        this.listingToView = response;
        this.eta = this.listingToView.expectedArrivalDate.toString().split('T')[0];


        for(let i = 1; i <= this.listingToView.quantity; i ++){
          this.quantities.push(i);
        }

        this.hasLoaded = true;
      },
      error: (error) => {
        this.retrieveListingError = true;
        console.log('************* retrieveListingByListingId error');
        this.location.back();
      }
    });
  }

  viewListing(): void {
    this.router.navigate(['/view-listing-details/' + this.listingId]);
  }

  getListingPicUrl(): string {
    return this.sessionService.getImageBaseUrl() + this.listingToView.photos[0];
  }

  doCreateTransaction() {
    console.log('Creating transaction..');
    this.transaction.seller = this.listingToView.createdBy;
    this.transaction.buyer = this.sessionService.getCurrentUser();
    this.transaction.createdOn = new Date();
    this.transaction.amount = this.listingToView.price;
    console.log('Transaction', this.transaction);

    this.transactionService.createTransaction(Number(this.listingId), this.transaction).subscribe({
      next: (response) => {

        console.log('Successful creation of transaction');
        const newTransactionId: number = response;
        this.transactionSuccessful = true;
        this.result = 1;
        this.resultError = false;
        this.message = 'Successfully requested to purchase listing';
        // crafting a new offer message
        const newMessage = new Message();
        newMessage.body = this.sessionService.getCurrentUser().username + ' has made an offer';
        newMessage.createdOn = new Date();
        newMessage.fromBuyer = true;
        newMessage.offerMessage = true;
        newMessage.readByBuyer = true;
        newMessage.readBySeller = false;
        newMessage.sentBy = this.sessionService.getCurrentUser().userId;
        console.log(newMessage);
        // need to check whether a convo has been created before sending it into the convo

        if (this.buyerCreditCard !== undefined) {
          this.transactionSuccessful = true;
        } else {
          this.transactionSuccessful = false;
        }


        let convo: Conversation;
        let buyingConvos: Conversation[];
        let convoNotFound: boolean;
        convoNotFound = true;
        // retrieving buyerconversations and checking if one exists
        this.conversationService.retrieveBuyerConversations().subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            buyingConvos = response;
            // check to see if there is existing convo
            // eslint-disable-next-line @typescript-eslint/prefer-for-of
            for (let i = 0; i < buyingConvos.length; i++) {
              console.log(buyingConvos);
              console.log(this.listingToView);
              console.log('enter for loop');
              if (buyingConvos[i].listing.listingId === this.listingToView.listingId) {
                // convo is found
                console.log('convo found');
                convoNotFound = false;
                convo = buyingConvos[i];
                this.conversationService.addMessage(newMessage, (Number(convo.convoId))).subscribe({
                  // eslint-disable-next-line @typescript-eslint/no-shadow
                  next: (response) => {
                    const newMessageId: number = response;
                    this.resultSuccess = true;
                    this.resultError = false;
                    this.message = 'New Message ' + newMessageId + ' created successfully';
                  },
                  //add message error
                  error: (error) => {
                    this.resultError = true;
                    this.resultSuccess = false;
                    this.message = 'An error has occurred while creating the new message: ' + error;

                    console.log('********** createNewMessage: ' + error);
                  }
                });
              }
            }
            if (convoNotFound) {
              console.log('need to cr8 new convo');
              //after for loop no existing convo le. need create new
              convo = new Conversation();
              convo.createdBy = this.sessionService.getCurrentUser();
              convo.listing = this.listingToView;
              convo.buyerUnread = 0;
              convo.sellerUnread = 0;
              convo.seller = this.listingToView.createdBy;
              convo.messages = new Array();
              convo.isOpen = true;
              console.log(convo);
              console.log(this.listingToView.listingId);
              console.log(this.sessionService.getCurrentUser().userId);
              this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)),
              (Number(this.sessionService.getCurrentUser().userId))).subscribe({
                // eslint-disable-next-line @typescript-eslint/no-shadow
                next: (response) => {
                  const newConversationId: number = response;
                  this.resultSuccess = true;
                  this.resultError = false;
                  this.message = 'New Conversation ' + newConversationId + ' created successfully';
                  this.conversationService.addMessage(newMessage, newConversationId).subscribe({
                    // eslint-disable-next-line @typescript-eslint/no-shadow
                    next: (response) => {
                      const newMessageId: number = response;
                      this.resultSuccess = true;
                      this.resultError = false;
                      this.message = 'New Message ' + newMessageId + ' created successfully';
                    },
                    //add message error
                    error: (error) => {
                      this.resultError = true;
                      this.resultSuccess = false;
                      this.message = 'An error has occurred while creating the new message: ' + error;
                      console.log('********** createNewMessage: ' + error);
                    }
                  });
                },
                error: (error) => {
                  this.resultError = true;
                  this.resultSuccess = false;
                  this.message = 'An error has occurred while creating the new conversation: ' + error;
                  console.log('********** createNewConversation: ' + error);
                }
              });
            }
          },

          error: (error) => {
            console.log('view-listing-chat-now (no buying convos at all):' + error);
            convo = new Conversation();
            convo.createdBy = this.sessionService.getCurrentUser();
            convo.listing = this.listingToView;
            convo.buyerUnread = 0;
            convo.sellerUnread = 0;
            convo.seller = this.listingToView.createdBy;
            convo.messages = new Array();
            convo.isOpen = true;
            this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)),
            (Number(this.sessionService.getCurrentUser().userId))).subscribe({
              // eslint-disable-next-line @typescript-eslint/no-shadow
              next: (response) => {
                console.log('service method success');
                const newConversationId: number = response;
                this.resultSuccess = true;
                this.resultError = false;
                this.message = 'New Conversation ' + newConversationId + ' created successfully';
                this.conversationService.addMessage(newMessage, newConversationId).subscribe({
                  // eslint-disable-next-line @typescript-eslint/no-shadow
                  next: (response) => {
                    const newMessageId: number = response;
                    this.resultSuccess = true;
                    this.resultError = false;
                    this.message = 'New Message ' + newMessageId + ' created successfully';
                  },
                  //add message error
                  // eslint-disable-next-line @typescript-eslint/no-shadow
                  error: (error) => {
                    this.resultError = true;
                    this.resultSuccess = false;
                    this.message = 'An error has occurred while creating the new message: ' + error;
                    console.log('********** createNewMessage: ' + error);
                  }
                });
              },
              // eslint-disable-next-line @typescript-eslint/no-shadow
              error: (error) => {
                this.resultError = true;
                this.resultSuccess = false;
                this.message = 'An error has occurred while creating the new conversation: ' + error;
                console.log('********** createNewConversation: ' + error);
              }
            });
          },
        });
      },

      //create transaction error
      error: (error) => {
        if(this.transaction.buyerCard === undefined && this.transaction.quantity === undefined){
          this.result= 4;
        } else if(this.transaction.buyerCard === undefined) {
          this.result = 2;
        } else {
          this.result = 3;
        }
        this.resultError = true;
        this.transactionSuccessful = false;
        this.message = 'Invalid Creation: Unexpected error occured. Try again later.';
        console.log('********** CreateNewTransactionPage: ' + error);
      }

    });



  }

  formatCreditCardNumber(ccNum: number) {
    const stringNum = ccNum.toString();
    return stringNum.substring(0, 4) + ' ' + stringNum.substring(4, 8) +
      ' ' + stringNum.substring(8, 12) + ' ' + stringNum.substring(12, 16);
  }

  formatCreditCardInfo(card: CreditCard): string {
    return card.ccBrand + ' - ' + this.formatCreditCardNumber(card.ccNumber);
  }

  formatListingTitle() {
    const maxNumberBeforeCutOff = 35;
    if (this.listingToView.title.length >= maxNumberBeforeCutOff) {
      return this.listingToView.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return this.listingToView.title;
  }

}
