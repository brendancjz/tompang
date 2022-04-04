import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { Listing } from '../models/listing';
import { User } from '../models/user';
import { Dispute } from '../models/dispute';
import { SessionService } from './session.service';
import { Observable, throwError } from 'rxjs';
import { CreateDisputeReq } from '../models/create-dispute-req';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class DisputeService {
  baseUrl = '/api/Dispute';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  createDispute(transactionId: number, dispute: Dispute) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    let createDisputeReq: CreateDisputeReq = new CreateDisputeReq(
      username,
      password,
      dispute,
      transactionId
    );
    return this.httpClient
      .put<number>(this.baseUrl, createDisputeReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  getUserDisputes(): Observable<Dispute[]> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Dispute[]>(
        this.baseUrl +
          '/retrieveAllUserDisputes?username=' +
          username +
          '&password=' +
          password
      )
      .pipe(catchError(this.handleError));
  }

  getDisputeById(disputeId: number): Observable<Dispute> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Dispute>(
        this.baseUrl +
          '/retrieveDispute?username=' +
          username +
          '&password=' +
          password +
          '&disputeId=' +
          disputeId
      )
      .pipe(catchError(this.handleError));
  }

  resolveDispute(disputeId: number) {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .post<any>(this.baseUrl, disputeId, httpOptions)
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
