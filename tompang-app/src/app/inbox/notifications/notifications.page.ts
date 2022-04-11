import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ToastController } from '@ionic/angular';
import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { Message } from 'src/app/models/message';
import { Transaction } from 'src/app/models/transaction';
import { ConversationService } from 'src/app/services/conversation.service';
import { Router } from '@angular/router';
import { ListingService } from 'src/app/services/listing.service';
import { TransactionService } from '../../services/transaction.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.page.html',
  styleUrls: ['./notifications.page.scss'],
})
export class NotificationsPage implements OnInit {

  notifications: Transaction[];
  allTransactions: Transaction[];

  constructor(private router: Router, private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private conversationService: ConversationService,
    private listingService: ListingService,
    private transactionService: TransactionService,
    public toastController: ToastController) {}

  ngOnInit() {
    this.notifications = [];
  }

  ionViewWillEnter() {
    console.log('IonViewWillEnter Offers');

    this.transactionService.getUserTransactions().subscribe({
      next: (response) => {
        this.allTransactions = response;

        // eslint-disable-next-line @typescript-eslint/prefer-for-of
        for (let i = 0; i < this.allTransactions.length; i++) {
          if (this.allTransactions[i].seller.userId === this.sessionService.getCurrentUser().userId &&
            !this.allTransactions[i].isAccepted && !this.allTransactions[i].isRejected) {
            this.notifications.push(this.allTransactions[i]);
          }
        }
      },
      error: (error) => {
        console.log('************* Error ' + error);
      }
    });

    console.log('Offers', this.notifications);
  }

  rejectOffer(transaction: Transaction) {
    this.transactionService.rejectTransaction(transaction.transactionId).subscribe({
      next: (response) => {
        console.log('TRANSACTION REJECTED');
        this.transactionService.getUserTransactions().subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            this.notifications = new Array();
            this.allTransactions = response;
            console.log('before for');

            // eslint-disable-next-line @typescript-eslint/prefer-for-of
            for (let i = 0; i < this.allTransactions.length; i++) {
              if (this.allTransactions[i].seller.userId === this.sessionService.getCurrentUser().userId &&
                !this.allTransactions[i].isAccepted && !this.allTransactions[i].isRejected) {
                console.log('pushed');
                this.notifications.push(this.allTransactions[i]);
              }
            }
            let conversations = Array();
            this.conversationService.retrieveSellerConversations().subscribe({
              // eslint-disable-next-line @typescript-eslint/no-shadow
              next: (response) => {
                console.log(response);

                conversations = response;
                console.log(conversations);
                console.log('retrieved all the conversations, need to lookup now');
                // eslint-disable-next-line @typescript-eslint/prefer-for-of
                for (let i = 0; i < conversations.length; i++) {
                  console.log(conversations[i].createdBy.userId);
                  console.log(transaction.buyer.userId);
                  console.log(conversations[i].listing.listingId);
                  console.log(transaction.listing.listingId);
                  if (conversations[i].createdBy.userId === transaction.buyer.userId &&
                    conversations[i].listing.listingId === transaction.listing.listingId) {
                    console.log('convo identified');
                    const newMessage = new Message();
                    newMessage.body = this.sessionService.getCurrentUser().username + ' has rejected your offer';
                    newMessage.createdOn = new Date();
                    newMessage.fromBuyer = false;
                    newMessage.offerMessage = false;
                    newMessage.readByBuyer = false;
                    newMessage.readBySeller = true;
                    newMessage.sentBy = this.sessionService.getCurrentUser().userId;
                    this.conversationService.addMessage(newMessage, conversations[i].convoId).subscribe({
                      // eslint-disable-next-line @typescript-eslint/no-shadow
                      next: (response) => {
                        console.log('Message added successfully');
                      },
                      error: (error) => {
                        console.log('Message failed to add');
                      }
                    });
                  }
                }
              },
              error: (error) => {
                console.log('error looking up convo');
              }
            });

            this.presentToast('Offer has been rejected!');
          },
          error: (error) => {

            console.log('************* error');
          }
        });
      },
      error: (error) => {

        console.log('REJECTING ERROR');
      }
    });
  }

  acceptOffer(transaction: Transaction) {
    this.transactionService.acceptTransaction(transaction.transactionId).subscribe({
      next: (response) => {
        console.log('TRANSACTION ACCEPTED');
        this.transactionService.getUserTransactions().subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            this.notifications = new Array();
            this.allTransactions = response;
            console.log('before for');

            // eslint-disable-next-line @typescript-eslint/prefer-for-of
            for (let i = 0; i < this.allTransactions.length; i++) {
              if (this.allTransactions[i].seller.userId === this.sessionService.getCurrentUser().userId &&
              !this.allTransactions[i].isAccepted && !this.allTransactions[i].isRejected) {
                console.log('pushed');
                this.notifications.push(this.allTransactions[i]);
              }
            }
            let conversations = Array();
            this.conversationService.retrieveSellerConversations().subscribe({
              // eslint-disable-next-line @typescript-eslint/no-shadow
              next: (response) => {
                conversations = response;
                console.log('retrieved all the conversations, need to lookup now');
                // eslint-disable-next-line @typescript-eslint/prefer-for-of
                for (let i = 0; i < conversations.length; i++) {
                  console.log(conversations[i].createdBy.userId);
                  console.log(transaction.buyer.userId);
                  console.log(conversations[i].listing.listingId);
                  console.log(transaction.listing.listingId);
                  if (conversations[i].createdBy.userId === transaction.buyer.userId &&
                    conversations[i].listing.listingId === transaction.listing.listingId) {
                    console.log('convo identified');
                    const newMessage = new Message();
                    newMessage.body = this.sessionService.getCurrentUser().username + ' has accepted your offer';
                    newMessage.createdOn = new Date();
                    newMessage.fromBuyer = false;
                    newMessage.offerMessage = false;
                    newMessage.readByBuyer = false;
                    newMessage.readBySeller = true;
                    newMessage.sentBy = this.sessionService.getCurrentUser().userId;
                    this.conversationService.addMessage(newMessage, conversations[i].convoId).subscribe({
                      // eslint-disable-next-line @typescript-eslint/no-shadow
                      next: (response) => {
                        console.log('message added successfully');
                      },
                      error: (error) => {
                        console.log('message failed to add');
                      }
                    });
                  }
                }
              },
              error: (error) => {
                console.log('error looking up convo');
              }
            });

            this.presentToast('Offer has been accepted!');
          },
          error: (error) => {

            console.log('************* error');
          }
        });
      },
      error: (error) => {

        console.log('ACCEPTING ERROR');
      }
    });
  }

  viewNotification(transaction: Transaction) {
    console.log('Viewing notification...');
  }

  async presentToast(messageToDispaly: string) {
    const toast = await this.toastController.create({
      message: messageToDispaly,
      duration: 2000
    });
    toast.animated = true;
    toast.color = 'light';
    toast.position = 'top';
    toast.present();
  }

}
