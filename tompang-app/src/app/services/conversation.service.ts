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
    return sampleConversation;
  }
}
