import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditCard } from '../models/creditCard';
import { Listing } from '../models/listing';
import { Transaction } from '../models/transaction';
import { User } from '../models/user';
import { Message } from '../models/message';
import { Conversation } from '../models/conversation'
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
  resultSuccess: boolean;


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
    // this.listingService.getAllAvailableListings().subscribe({
    //   next: (response) => {
    //     this.listingToView = response[9];
    //     this.eta = this.listingToView.expectedArrivalDate.toString().split('T')[0];
    //     this.hasLoaded = true;
    //   },
    //   error: (error) => {
    //     this.retrieveListingError = true;
    //     console.log('********** View Listing Details Page.ts: ' + error);
    //   }
    // });
    this.listingService.getListingByListingId(parseInt(this.listingId)).subscribe({
      next: (response) => {
        this.listingToView = response;
        this.eta = this.listingToView.expectedArrivalDate.toString().split('T')[0];
        this.hasLoaded = true;
      },
      error: (error) => {
        this.retrieveListingError = true;
        console.log('************* retrieveListingByListingId error');
      }
    })
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
      next: (response) => {

        console.log('Successful creation of transaction');
        const newTransactionId: number = response;
        this.transactionSuccessful = true;
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

        let convo: Conversation;
        let buyingConvos: Conversation[];
        let convoNotFound: Boolean;
        convoNotFound = true;
        // retrieving buyerconversations and checking if one exists
        this.conversationService.retrieveBuyerConversations().subscribe({
          next: (response) => {
            buyingConvos = response;
            // check to see if there is existing convo
            for (let i = 0; i < buyingConvos.length; i++) {
              console.log(buyingConvos);
              console.log(this.listingToView);
              console.log('enter for loop');
              if (buyingConvos[i].listing.listingId == this.listingToView.listingId) {
                // convo is found
                console.log('convo found');
                convoNotFound = false;
                convo = buyingConvos[i];
                this.conversationService.addMessage(newMessage, (Number(convo.convoId))).subscribe({
                  next: (response) => {
                    let newMessageId: number = response;
                    this.resultSuccess = true;
                    this.resultError = false;
                    this.message = "New Message " + newMessageId + " created successfully";
                    this.conversationService.getConversationById(convo.convoId).subscribe({
                      next: (response) => {
                        // this.convoToView = response;
                        console.log(response);
                        // console.log(this.convoToView);
                        this.hasLoaded = true;
                        console.log("PASS");
                        // document.getElementById("divExample").style.bottom = '0';
                      },
                      error: (error) => {
                        console.log('view-conversation.page.ts:' + error);
                        console.log("FAIL");
                      },
                    });
                  },
                  //add message error
                  error: (error) => {
                    this.resultError = true;
                    this.resultSuccess = false;
                    this.message = "An error has occurred while creating the new message: " + error;

                    console.log('********** createNewMessage: ' + error);
                  }
                });
                //*********************************************************************************** need add message to convo and send no need navigate
                // this.router.navigate(['/view-conversation/' + convo.convoId]);
                break;
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
              this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)), (Number(this.sessionService.getCurrentUser().userId))).subscribe({
                next: (response) => {
                  let newConversationId: number = response;
                  this.resultSuccess = true;
                  this.resultError = false;
                  this.message = "New Conversation " + newConversationId + " created successfully";
                  //*********************************************************************************** need add message to convo and send no need navigate
                  this.conversationService.addMessage(newMessage, (Number(convo.convoId))).subscribe({
                    next: (response) => {
                      let newMessageId: number = response;
                      this.resultSuccess = true;
                      this.resultError = false;
                      this.message = "New Message " + newMessageId + " created successfully";
                      this.conversationService.getConversationById(convo.convoId).subscribe({
                        next: (response) => {
                          // this.convoToView = response;
                          console.log(response);
                          // console.log(this.convoToView);
                          this.hasLoaded = true;
                          console.log("PASS");
                          // document.getElementById("divExample").style.bottom = '0';
                        },
                        error: (error) => {
                          console.log('view-conversation.page.ts:' + error);
                          console.log("FAIL");
                        },
                      });
                    },
                    //add message error
                    error: (error) => {
                      this.resultError = true;
                      this.resultSuccess = false;
                      this.message = "An error has occurred while creating the new message: " + error;

                      console.log('********** createNewMessage: ' + error);
                    }
                  });
                  // this.router.navigate(['/view-conversation/' + newConversationId]);
                },
                error: (error) => {
                  this.resultError = true;
                  this.resultSuccess = false;
                  this.message = "An error has occurred while creating the new conversation: " + error;
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
            this.conversationService.createConversation(convo, (Number(this.listingToView.listingId)), (Number(this.sessionService.getCurrentUser().userId))).subscribe({
              next: (response) => {
                console.log('service method success');
                let newConversationId: number = response;
                this.resultSuccess = true;
                this.resultError = false;
                this.message = "New Conversation " + newConversationId + " created successfully";
                //*********************************************************************************** need add message to convo and send no need navigate
                this.conversationService.addMessage(newMessage, (Number(convo.convoId))).subscribe({
                  next: (response) => {
                    let newMessageId: number = response;
                    this.resultSuccess = true;
                    this.resultError = false;
                    this.message = "New Message " + newMessageId + " created successfully";
                    this.conversationService.getConversationById(convo.convoId).subscribe({
                      next: (response) => {
                        // this.convoToView = response;
                        console.log(response);
                        // console.log(this.convoToView);
                        this.hasLoaded = true;
                        console.log("PASS");
                        // document.getElementById("divExample").style.bottom = '0';
                      },
                      error: (error) => {
                        console.log('view-conversation.page.ts:' + error);
                        console.log("FAIL");
                      },
                    });
                  },
                  //add message error
                  error: (error) => {
                    this.resultError = true;
                    this.resultSuccess = false;
                    this.message = "An error has occurred while creating the new message: " + error;

                    console.log('********** createNewMessage: ' + error);
                  }
                });
                this.router.navigate(['/view-conversation/' + newConversationId]);
              },
              error: (error) => {
                this.resultError = true;
                this.resultSuccess = false;
                this.message = "An error has occurred while creating the new conversation: " + error;
                console.log('********** createNewConversation: ' + error);
              }
            });
          },
        });
      },

      //create transaction error
      error: (error) => {
        this.resultError = true;
        this.transactionSuccessful = false;
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
    return stringNum.substring(0, 4) + ' ' + stringNum.substring(4, 8) +
      ' ' + stringNum.substring(8, 12) + ' ' + stringNum.substring(12, 16);
  }

  formatCreditCardInfo(card: CreditCard): string {
    return card.ccBrand + ' - ' + this.formatCreditCardNumber(card.ccNumber);
  }

}
