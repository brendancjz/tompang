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
import { Dispute } from '../models/dispute';
import { ListingService } from './listing.service';
import { Transaction } from '../models/transaction';
import { CreateTransactionReq } from '../models/create-transaction-req';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  baseUrl = '/api/Transaction';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  getTransactionById(transactionId: number): Observable<Transaction> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Transaction>(
        this.baseUrl +
          '/retrieveTransaction?username=' +
          username +
          '&password=' +
          password +
          '&transactionId=' +
          transactionId
      )
      .pipe(catchError(this.handleError));
  }

  getUserTransactions(): Observable<Transaction[]> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Transaction[]>(
        this.baseUrl +
          '/retrieveAllUserTransactions?username=' +
          username +
          '&password=' +
          password
      )
      .pipe(catchError(this.handleError));
  }

  createTransaction(listingId: number, newTransaction: Transaction) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    const userId = currentUser.userId;

    let createTransactionReq: CreateTransactionReq = new CreateTransactionReq(
      username,
      password,
      newTransaction,
      listingId
    );

    return this.httpClient
      .put<number>(this.baseUrl, createTransactionReq, httpOptions)
      .pipe(catchError(this.handleError));
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
