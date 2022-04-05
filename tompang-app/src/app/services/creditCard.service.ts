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
import { CreditCard } from '../models/creditCard';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class CreditCardService {
  baseUrl = '/api/CreditCard';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService,
    private listingService: ListingService) {
  }


  getCreditCardById(ccId: number) {
    //Implement bridge

    const sampleCreditCard = new CreditCard(1, 'DBS', 'BRENDAN CHIA', 4605123412341234, 123, new Date());

    return sampleCreditCard;
  }

  deleteCreditCard(ccId: number): boolean {

    const currentUser = this.sessionService.getCurrentUser();

    //Implement

    //set currentUser with updated user

    return true;
  }
}
