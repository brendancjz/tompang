import { Component, OnInit } from '@angular/core';
import { CategoryEnum } from '../models/CategoryEnum';
import { Listing } from '../models/listing';
import { ListingService } from '../services/listing.service';

@Component({
  selector: 'app-create-listing',
  templateUrl: './create-listing.page.html',
  styleUrls: ['./create-listing.page.scss'],
})
export class CreateListingPage implements OnInit {
  title: string | undefined;
  // should countries and cities be a selected option
  country: string | undefined;
  city: string | undefined;
  description: string | undefined;
  category: string | undefined;
  price: number | undefined;
  quantity: number | undefined;
  creationError: boolean;
  creationErrorMessage: string | undefined;

  categories: String[];

  constructor(private listingService: ListingService) {
    this.categories = [
      'FOOD',
      'APPAREL',
      'ACCESSORIES',
      'FOOTWEAR',
      'GIFTS',
      'ELECTRONICS',
    ];
  }

  ngOnInit() {}

  createListing(): void {
    if (
      this.title === undefined || this.country === undefined || this.city === undefined || this.description === undefined || this.category === undefined || this.price === undefined || this.quantity === undefined
    ) {
      this.creationError = true;
      this.creationErrorMessage = "Incomplete fields, couldn't create listing."
      return;
    }

    const listing: Listing = new Listing();
    listing.title = this.title;
    listing.country = this.country;
    listing.city = this.city;
    listing.description = this.description;
    listing.category = this.category;
    listing.price = this.price;
    listing.quantity = this.quantity;

    console.log(listing);
  }
}
