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
  searchTerm: string;

  currentUser: User;
  myLikedListings: Listing[];

  constructor(public sessionService: SessionService,
    public listingService: ListingService
  ) {

    this.currentUser = sessionService.getCurrentUser();
    this.myLikedListings = listingService.getUserLikedListings(this.currentUser);

   }

  ngOnInit() {
  }
}
