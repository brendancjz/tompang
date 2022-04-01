import { Component, OnInit } from '@angular/core';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';
  title: string | undefined;
  currentUser: User;
  username: string | undefined;


  sampleListing: Listing;

  myListings: Listing[];

  constructor(
    public sessionService: SessionService,
    private listingService: ListingService
  ) {
    const listing = sessionService.getListing();

    this.title = listing.title;
    this.currentUser = sessionService.getCurrentUser();
    this.username = this.currentUser.username;

    this.sampleListing = new Listing(1, 'Singapore', 'Singapore', 'Bape T-Shirt',
  'Get yo bape t-shirts today!', 'APPAREL', 100, new Date(), 5);

    this.myListings = [this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing,
      this.sampleListing, this.sampleListing, this.sampleListing, this.sampleListing];

  }

  ngOnInit() {}

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
