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
  myLikedListings: Listing[] = [];
  message: string;

  constructor(
    public sessionService: SessionService,
    public listingService: ListingService
  ) {
    this.currentUser = sessionService.getCurrentUser();
  }

  ngOnInit() {}

  ionViewWillEnter() {
    this.myLikedListings = [];
    console.log('IonViewWillEnter LikedListings');
    this.doViewLikedListings();
  }

  doViewLikedListings() {
    this.listingService.getAllAvailableListings().subscribe({
      next: (response) => {
        response.map((listing) => {
          console.log(listing);
          listing.likedByUsers.map((user) => {
            if (user.userId === this.currentUser.userId) {
              this.myLikedListings.push(listing);
            }
          });
        });
      },
      error: (error) => {
        this.message =
          'An error has occurred while creating the new conversation: ' + error;
        console.log('********** createNewConversation: ' + error);
      },
    });
  }
}
