import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Listing } from '../models/listing';
import { ListingService } from '../services/listing.service';

import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.page.html',
  styleUrls: ['./shop.page.scss'],
})
export class ShopPage implements OnInit {
  samplePic = '../../assets/images/uploadedFiles/japanese_biscuits.jpeg';
  basePictureUrl = '../../assets/images';

  searchTerm: string;
  allAvailableListings: Listing[];
  mostLikedListings: Listing[];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    public listingService: ListingService
  ) {
    this.mostLikedListings = new Array();
  }

  ngOnInit(): void {
    this.refreshListings();

    this.listingService.getAllAvailableListings().subscribe({
      next: (response) => {
        this.allAvailableListings = response;
      },
      error: (error) => {
        console.log('getAllAvailableListings.ts:' + error);
      },
    });

    this.listingService.getMostLikedListings().subscribe({
      next: (response) => {
        this.mostLikedListings = response; //Top 5 listings

        this.mostLikedListings
          .sort((l1, l2) => {
            if (l1.likedByUsers.length > l2.likedByUsers.length) {
              return -1;
            } else if (l1.likedByUsers.length < l2.likedByUsers.length) {
              return 1;
            } else {
              return 1;
            }
          })
          .slice(5);
        //slicing not working.. will fix tmr
      },
      error: (error) => {
        console.log('getMostLikedListings.ts:' + error);
      },
    });
  }

  ionViewWillEnter() {
    this.refreshListings();
  }

  ionViewDidEnter() {
    this.refreshListings();
    console.log('*** refreshed listings');
  }

  //Need to repeat this method in the Footer page as well.
  createListingPage() {
    console.log('Going to create listing page');
  }

  //Need to repeat this method in the Footer page as well.
  profilePage() {
    const currentUser = this.sessionService.getCurrentUser();
    this.router.navigate(['/profile/' + currentUser.userId]);
  }

  refreshListings() {
    this.listingService.getAllAvailableListings().subscribe({
      next: (response) => {
        this.allAvailableListings = response;
      },
      error: (error) => {
        console.log('******* Shops Page: ' + error);
      },
    });

    this.listingService.getMostLikedListings().subscribe({
      next: (response) => {
        this.mostLikedListings = response; //Top 5 listings

        this.mostLikedListings
          .sort((l1, l2) => {
            if (l1.likedByUsers.length > l2.likedByUsers.length) {
              return -1;
            } else if (l1.likedByUsers.length < l2.likedByUsers.length) {
              return 1;
            } else {
              return 1;
            }
          })
          .slice(5);
        //slicing not working.. will fix tmr
      },
      error: (error) => {
        console.log('getMostLikedListings.ts:' + error);
      },
    });
  }
}
