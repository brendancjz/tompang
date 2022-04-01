import { Component, OnInit } from '@angular/core';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-liked-listings',
  templateUrl: './liked-listings.page.html',
  styleUrls: ['./liked-listings.page.scss'],
})
export class LikedListingsPage implements OnInit {
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';
  sampleListing: Listing;

  myLikedListings: Listing[];

  constructor(public sessionService: SessionService,
    private listingService: ListingService
  ) {

      this.sampleListing = new Listing(1, 'Singapore', 'Singapore', 'Bape T-Shirt',
  'Get yo bape t-shirts today!', 'APPAREL', 100, new Date(), 5);

      this.myLikedListings = [this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing, this.sampleListing];

   }

  ngOnInit() {
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
