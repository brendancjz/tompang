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
    console.log('********* ngOnInit in shop **********');

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
        this.mostLikedListings = response.slice(0, 5); //Top 5 listings
      },
      error: (error) => {
        console.log('getAllAvailableListings.ts:' + error);
      },
    });

    this.mostLikedListings.sort((l1, l2) => {
      if (l1.numOfLikes < l2.numOfLikes) {
        return -1;
      } else if (l1.numOfLikes > l2.numOfLikes) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  ionViewWillEnter() {
    this.refreshListings();
  }

  ionViewDidEnter() {
    console.log('********** Shop Page.ionViewDidEnter() ');
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
  }
}
