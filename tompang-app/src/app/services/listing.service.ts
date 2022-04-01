import { Injectable } from '@angular/core';

import { Listing } from '../models/listing';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class ListingService {
  listings: Listing[];

  constructor() {
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

  getMostLikedListings(): Listing[] {
    return this.getSampleListings();
  }

  getAllAvailableListings(): Listing[] {
    return this.getSampleListings();
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

}
