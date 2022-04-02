import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { Listing } from '../models/listing';
import { User } from '../models/user';
import { SessionService } from './session.service';
import { Observable, throwError } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class ListingService {
  baseUrl: string = "/api/Listing";
  listings: Listing[];

  constructor(private httpClient: HttpClient, private sessionService: SessionService) {
    this.listings = new Array();
  }

  createListing(): Listing | null {
    const listing = new Listing(
      1,
      'Japan',
      'Tokyo',
      'Tokyo Milk Cheese Factory Biscuits',
      'Lovely biscuits from Tokyo, Japan.',
      'FOOD',
      25,
      new Date(),
      10
    );
    return listing;
  }

  getSampleListings() {
    const sampleListing = new Listing(1, 'Singapore', 'Singapore', 'Bape T-Shirt',
      'Get yo bape t-shirts today!', 'APPAREL', 100, new Date(), 5);

    const sampleListings = [
      sampleListing, sampleListing, sampleListing,
      sampleListing, sampleListing, sampleListing,
      sampleListing, sampleListing, sampleListing,
    ];

    return sampleListings;
  }

  getUserListings(user: User): Listing[] {
    return this.getSampleListings();
  }

  getUserLikedListings(user: User): Listing[] {
    return this.getSampleListings();
  }

  getMostLikedListings(): Observable<Listing[]> {
    return this.httpClient.get<Listing[]>(this.baseUrl + "/retrieveAllListings?username=manager&password=password").pipe
      (
        catchError(this.handleError)
      );
  }

  getAllAvailableListings(): Observable<Listing[]> {
    return this.httpClient.get<Listing[]>(this.baseUrl + "/retrieveAllListings?username=manager&password=password").pipe
      (
        catchError(this.handleError)
      );
  }

  viewListingDetails(listingToView: Listing) {
    console.log('View listing details for listingId: ' + listingToView.listingId);
  }

  likeListing(listing: Listing) {
    console.log('Liking listing..');
  }

  unlikeListing(listing: Listing) {
    console.log('unliking listing..');
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";
    if (error.error instanceof ErrorEvent) {
      errorMessage = "An Unknown error has occured: " + error.error;
    } else {
      errorMessage = "A HTTP error has occured: " + `HTTP ${error.status}: ${error.error}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
