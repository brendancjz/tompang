import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { Listing } from '../models/listing';
import { User } from '../models/user';
import { SessionService } from './session.service';
import { Observable, throwError } from 'rxjs';
import { Conversation } from '../models/conversation';
import { ListingService } from './listing.service';
import { Message } from 'src/app/models/message';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class ConversationService {
  baseUrl = '/api/Conversation';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private listingService: ListingService) {
  }

  retrieveBuyerConversations(): Observable<Conversation[]>{
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    console.log(username);
    console.log(password);
    return this.httpClient.get<Conversation[]>(this.baseUrl + '/retrieveBuyerConversations?username=' + username + '&password=' + password).pipe(catchError(this.handleError));
  }

  retrieveSellerConversations(): Observable<Conversation[]> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    return this.httpClient.get<Conversation[]>(this.baseUrl + '/retrieveSellerConversations?username=' + username + '&password=' + password).pipe(catchError(this.handleError));
  }

  getBuyerConversationWithListing(userId: number, listingId: number): Conversation {
    //Implement bridge

    const sampleConversation = new Conversation(10);
    sampleConversation.createdBy = this.sessionService.getCurrentUser();
    sampleConversation.listing = this.listingService.getSampleListing();
    return sampleConversation;
  }

  getConversationById(convoId: number) {
    //Implement bridge
    const sampleConversation = new Conversation(10);
    sampleConversation.createdBy = this.sessionService.getCurrentUser();
    sampleConversation.listing = this.listingService.getSampleListing();
    const initiateMessage1 = new Message(1, "Hi can I buy this?", true, this.sessionService.getCurrentUser().userId, true, false, false);
    const replyMessage1 = new Message(2, "Sure, you have to make an offer!", false, 50, false, true, false);


    if (convoId === 50) {
      console.log("ENter 51");
      const sampleConvo1 = new Conversation(50);
      sampleConvo1.createdBy = new User(50, 'Buyer', 'One', 'buyer1', 'password',
        'buyer1@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
      sampleConvo1.listing = new Listing(5, 'Singapore', 'Singapore',
        'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
        'GIFTS', 40.00, new Date(), 2);
      sampleConvo1.messages.push(initiateMessage1);
      sampleConvo1.messages.push(replyMessage1);
      return sampleConvo1;
    } else if (convoId === 51) {
      const sampleConvo2 = new Conversation(51);
      sampleConvo2.createdBy = new User(51, 'Buyer', 'Two', 'buyer2', 'password',
        'buyer2@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
      sampleConvo2.listing = new Listing(5, 'Singapore', 'Singapore',
        'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
        'GIFTS', 40.00, new Date(), 2);
      sampleConvo2.messages.push(initiateMessage1);
      sampleConvo2.messages.push(replyMessage1);
      return sampleConvo2;
    } else if (convoId === 52) {
      console.log("ENter 52");
      const sampleConvo3 = new Conversation(52);
      sampleConvo3.createdBy = new User(52, 'Buyer', 'Three', 'buyer3', 'password',
        'buyer3@gmail.com', new Date(), '/uploadedFiles/default_picture.jpg', 54631212);
      sampleConvo3.listing = new Listing(5, 'Singapore', 'Singapore',
        'Keyboard Mechanical Tokyo Japan HOT Fire', 'Hello there mate. Would you like to buy this dumbbell set from Singapore?',
        'GIFTS', 40.00, new Date(), 2);
      sampleConvo3.messages.push(initiateMessage1);
      sampleConvo3.messages.push(replyMessage1);
      return sampleConvo3;
    }
    return sampleConversation;
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An Unknown error has occured: ' + error.error;
    } else {
      errorMessage =
        'A HTTP error has occured: ' + `HTTP ${error.status}: ${error.error}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
