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
import { Message } from '../models/message';
import { NewMessageReq } from '../models/new-message-req';
import { NewConversationReq } from '../models/new-conversation-req';

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

  retrieveBuyerConversations(): Observable<Conversation[]> {
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

  // getBuyerConversationWithListing(userId: number, listingId: number): Conversation {
  //   //Implement bridge

  //   const sampleConversation = new Conversation(10);
  //   sampleConversation.createdBy = this.sessionService.getCurrentUser();
  //   sampleConversation.listing = this.listingService.getSampleListing();
  //   return sampleConversation;
  // }

  getConversationById(convoId: number): Observable<Conversation> {
    //Implement bridge
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    console.log(convoId);
    return this.httpClient.get<Conversation>(this.baseUrl + '/retrieveConversation/' + convoId + '?username=' + username + '&password=' + password).pipe(catchError(this.handleError));
  }

  addMessage(newMessage: Message, convoId: number) {
    console.log('service method called');
    console.log(convoId);
    let newMessageReq: NewMessageReq = new NewMessageReq(newMessage, convoId);
    return this.httpClient.put<number>(this.baseUrl, newMessageReq, httpOptions).pipe(catchError(this.handleError));
  }

  createConversation(newConversation: Conversation, listingId: number, userId: number) {
    console.log(' create convo service method called');
    let newConversationReq: NewConversationReq = new NewConversationReq(newConversation, listingId, userId);
    return this.httpClient.put<number>(this.baseUrl + '/createConversation', newConversationReq, httpOptions).pipe(catchError(this.handleError));
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
