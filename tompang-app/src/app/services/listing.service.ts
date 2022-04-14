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
import { CreateListingPage } from '../create-listing/create-listing.page';
import { CreateListingReq } from '../models/create-listing-req';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class ListingService {
  baseUrl = '/api/Listing';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  createListing(newListing: Listing): Observable<number> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    const createListingReq: CreateListingReq = new CreateListingReq(
      username,
      password,
      newListing
    );

    return this.httpClient
      .put<number>(this.baseUrl, createListingReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateListing(listing: Listing): Observable<any> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    const createListingReq: CreateListingReq = new CreateListingReq(
      username,
      password,
      listing
    );

    return this.httpClient
      .post<any>(this.baseUrl, createListingReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  deleteListing(listingId: number): Observable<any>
  {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;
    return this.httpClient.delete<any>(this.baseUrl + '/' + listingId + '?username=' + username + '&password=' + password).pipe
    (
      catchError(this.handleError)
    );
  }

  getUserListings(user: User): Observable<Listing[]> {
    const username = user.username;
    const password = user.password;

    return this.httpClient
      .get<Listing[]>(
        this.baseUrl +
          '/retrieveAllUserListings?username=' +
          username +
          '&password=' +
          password
      )
      .pipe(catchError(this.handleError));
  }

  getListingByListingId(listingId: number): Observable<Listing> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Listing>(
        this.baseUrl +
          '/retrieveListing/' +
          listingId +
          '?username=' +
          username +
          '&password=' +
          password +
          '&listingId=' +
          listingId
      )
      .pipe(catchError(this.handleError));
  }

  getMostLikedListings(): Observable<Listing[]> {
    return this.getAllAvailableListings();
  }

  getAllAvailableListings(): Observable<Listing[]> {
    const currentUser = this.sessionService.getCurrentUser();
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .get<Listing[]>(
        this.baseUrl +
          '/retrieveAllListings?username=' +
          username +
          '&password=' +
          password
      )
      .pipe(catchError(this.handleError));
  }

  viewListingDetails(listingToView: Listing) {
    console.log(
      'View listing details for listingId: ' + listingToView.listingId
    );
  }

  likeListing(listing: Listing) {
    const currentUser = this.sessionService.getCurrentUser();
    const userId = currentUser.userId;
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .put(
        this.baseUrl +
          '/likeListing/' +
          listing.listingId +
          '/' +
          userId +
          '?username=' +
          username +
          '&password=' +
          password,
        {},
        httpOptions
      )
      .pipe(catchError(this.handleError));
  }

  unlikeListing(listing: Listing) {
    const currentUser = this.sessionService.getCurrentUser();
    const userId = currentUser.userId;
    const username = currentUser.username;
    const password = currentUser.password;

    return this.httpClient
      .put(
        this.baseUrl +
          '/unlikeListing/' +
          listing.listingId +
          '/' +
          userId +
          '?username=' +
          username +
          '&password=' +
          password,
        {},
        httpOptions
      )
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
