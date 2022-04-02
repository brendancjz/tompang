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
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';

  allAvailableListings: Listing[];
  mostLikedListings: Listing[];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    public listingService: ListingService
  ) {
    this.mostLikedListings = new Array();
    this.allAvailableListings = new Array();
  }

  ngOnInit(): void {
    console.log('********* ngOnInit in shop **********');

    this.listingService.getAllAvailableListings().subscribe({
      next: (response) => {
        this.allAvailableListings = response;
      },
      error: (error) => {
        console.log('getAllAvailableListings.ts:' + error);
      },
    });

    this.listingService.getAllAvailableListings().subscribe({
      next: (response) => {
        this.mostLikedListings = response;

        // sorting the listings and assigning it to mostLikedListings
        this.mostLikedListings.sort((l1, l2) => {
          console.log('**** getting sorted *****');

          if (l1.numOfLikes > l2.numOfLikes) {
            return -1;
          } else if (l1.numOfLikes < l2.numOfLikes) {
            return 1;
          } else {
            return 0;
          }
        });

        this.mostLikedListings.map((listing) => {
          console.log(
            listing.title + ', number of likes: ' + listing.numOfLikes
          );
        });
      },
      error: (error) => {
        console.log('getMostLikedListings.ts:' + error);
      },
    });
  }

  //Need to repeat this method in the Footer page as well.
  createListingPage() {
    console.log('Going to create listing page');
  }

  //Need to repeat this method in the Footer page as well.
  profilePage() {
    this.router.navigate(['/profile']);
  }
}
