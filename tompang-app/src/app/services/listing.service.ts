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

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class ListingService {
  baseUrl = '/api/Listing';

  username: string;
  password: string;

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService) {

    const currentUser = sessionService.getCurrentUser();
    this.username = currentUser.username;
    this.password = currentUser.password;
  }

  createListing(newListing: Listing): number {
    const newListingId = 1;

    //Code here

    return newListingId;
  }

  getSampleListings() {
    const sampleListing = new Listing(
      1,
      'Singapore',
      'Singapore',
      'Bape T-Shirt',
      'Get yo bape t-shirts today!',
      'APPAREL',
      100,
      new Date(),
      5
    );

    const sampleListing2 = new Listing(
      2,
      'Malaysia',
      'Johor Bahru',
      'Skateboard',
      'Hello everyone, I am going to malaysia this weekend.' +
      'Heard there are some skateboards there. Lmk if you want one.',
      'GIFTS',
      75,
      new Date(),
      5
    );

    sampleListing.photos.push('/tompang_icon_logo_blue.png');
    sampleListing2.photos.push('/uploadedFiles/default_picture.jpg');

    const sampleListings = [
      sampleListing,
      sampleListing2,
      sampleListing,
      sampleListing2,
      sampleListing2,
      sampleListing2,
      sampleListing,
      sampleListing2,
      sampleListing,
    ];

    return sampleListings;
  }

  getUserListings(user: User): Listing[] {
    return this.getSampleListings();
  }

  getUserLikedListings(user: User): Listing[] {
    return this.getSampleListings();
  }

  getListingByListingId(listingId: number): Observable<Listing> {
    return this.httpClient.get<Listing>(this.baseUrl + '/retrieveListing?username='
    + this.username + '&password=' + this.password + '&listingId=' + listingId).pipe
    (
      catchError(this.handleError)
    );
  }

  getMostLikedListings(): Observable<Listing[]> {
    return this.getAllAvailableListings();
  }

  getAllAvailableListings(): Observable<Listing[]> {
    return this.httpClient.get<Listing[]>(this.baseUrl + '/retrieveAllListings?username=' +
    this.username + '&password=' + this.password).pipe
      (
        catchError(this.handleError)
      );
  }

  viewListingDetails(listingToView: Listing) {
    console.log(
      'View listing details for listingId: ' + listingToView.listingId
    );
  }

  likeListing(listing: Listing) {
    console.log('Liking listing..');
  }

  unlikeListing(listing: Listing) {
    console.log('unliking listing..');
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An Unknown error has occured: ' + error.error;
    } else {
      errorMessage = 'A HTTP error has occured: ' + `HTTP ${error.status}: ${error.error}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
