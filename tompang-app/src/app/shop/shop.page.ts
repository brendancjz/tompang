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

  mostLikedListings: Listing[];
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';

  allAvailableListings: Listing[];

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    public listingService: ListingService) {

      this.mostLikedListings = listingService.getMostLikedListings();
      this.allAvailableListings = listingService.getAllAvailableListings();

     }

  ngOnInit() {
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
