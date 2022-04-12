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
import { UpdateTransactionReq } from '../models/update-transaction-req';


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
          '/retrieveTransaction/' +
          transactionId +
          '?username=' +
          username +
          '&password=' +
          password
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

  createTransaction(
    listingId: number,
    newTransaction: Transaction
  ): Observable<number> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    console.log(listingId);
    const createTransactionReq: CreateTransactionReq = new CreateTransactionReq(
      username,
      password,
      listingId,
      newTransaction
    );

    return this.httpClient
      .put<number>(this.baseUrl, createTransactionReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  completeTransaction(transactionId: number) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    console.log(username);

    const updateTransactionReq: UpdateTransactionReq = new UpdateTransactionReq(
      username,
      password,
      transactionId
    );

    return this.httpClient
      .post<any>(this.baseUrl, updateTransactionReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  acceptTransaction(transactionId: number) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    const updateTransactionReq: UpdateTransactionReq = new UpdateTransactionReq(
      username,
      password,
      transactionId
    );

    return this.httpClient
      .post<any>(this.baseUrl + '/acceptTransaction', updateTransactionReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  rejectTransaction(transactionId: number) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    const updateTransactionReq: UpdateTransactionReq = new UpdateTransactionReq(
      username,
      password,
      transactionId
    );

    return this.httpClient
      .post<any>(this.baseUrl + '/rejectTransaction', updateTransactionReq, httpOptions)
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
