import { Injectable } from '@angular/core';

import { Listing } from '../models/listing';

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
}
