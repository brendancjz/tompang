import { Component, OnInit } from '@angular/core';
import { Listing } from '../models/listing';
import { ListingService } from '../services/listing.service';

@Component({
  selector: 'app-create-listing',
  templateUrl: './create-listing.page.html',
  styleUrls: ['./create-listing.page.scss'],
})
export class CreateListingPage implements OnInit {
  title: string | undefined;
  country: string | undefined;
  city: string | undefined;
  description: string | undefined;
  category: string | undefined;
  price: number | undefined;
  quantity: number | undefined;

  creationError: boolean;
  creationErrorMessage: string | undefined;
  creationSuccessful: boolean;

  categories: string[];
  countries: string[];
  cities: string[];
  countryCityMap = {};

  constructor(private listingService: ListingService) {
    this.categories = [
      'FOOD',
      'APPAREL',
      'ACCESSORIES',
      'FOOTWEAR',
      'GIFTS',
      'ELECTRONICS',
    ];
    this.countries = [
      'Japan',
      'Korea',
      'Malaysia',
      'Singapore',
      'USA'
    ];

    this.countryCityMap = this.initialiseCountriesCities();
  }

  ngOnInit() {}

  createListing(): void {
    this.doValidation();
    if (this.creationError) {
      return;
    }

    console.log('Passed validations. Creating listing...');
    const listing: Listing = new Listing();
    listing.title = this.title;
    listing.country = this.country;
    listing.city = this.city;
    listing.description = this.description;
    listing.category = this.category;
    listing.price = this.price;
    listing.quantity = this.quantity;

    this.listingService.createListing(listing);

    this.resetInputs();
    console.log(listing);
  }

  resetInputs(): void {
    this.title = undefined;
    this.country = undefined;
    this.city = undefined;
    this.description = undefined;
    this.category = undefined;
    this.price = undefined;
    this.quantity = undefined;
  }

  doValidation(): void {
    if (
      this.title === undefined || this.country === undefined ||
      this.city === undefined || this.description === undefined ||
      this.category === undefined || this.price === undefined ||
      this.quantity === undefined
    ) {
      this.creationError = true;
      this.creationErrorMessage = 'Incomplete fields, could not create listing.';
      return;
    }

    if (this.price <= 0) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, Price cannot be negative or zero.';
      return;
    }

    if (this.quantity <= 0) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, Quantity cannot be negative or zero';
      return;
    }

    if (this.description.length <= 20) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, please add more description.';
      return;
    }

    if (this.description.length >= 500) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, Description has exceeded word limit.';
      return;
    }

    if (this.title.length <= 5) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, please add more to your title.';
      return;
    }

    if (this.title.length >= 50) {
      this.creationError = true;
      this.creationErrorMessage = 'Sorry, Title has exceeded word limit.';
      return;
    }
  }

  selectCitiesFromCountry(country: string) {
    this.cities = this.countryCityMap[country];
  }

  initialiseCountriesCities() {
    const map = {};
    let countryCities = [];
    countryCities.push('Alabama','Alaska','Arizona','Arkansas',
    'California','Colorado','Connecticut','Delaware','Florida','Georgia',
    'Hawaii','Idaho','Illinois','Indiana','Iowa','Kansas','Kentucky',
    'Louisiana','Maine','Maryland','Massachusetts','Michigan','Minnesota',
    'Missouri','Montana','Nebraska','Nevada','New Hampshire','New Jersey',
    'New Mexico','New York','North Carolina','North Dakota','Ohio','Oklahoma',
    'Oregon','Pennsylvania','Rhode Island','South Carolina','South Dakota',
    'Tennessee','Texas','Utah','Vermont','Washington','West Virginia','Wisconsin',
    'Wyoming');
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['USA'] = countryCities;

    countryCities = [];
    countryCities.push(
      'Singapore'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['SINGAPORE'] = countryCities;

    countryCities = [];
    countryCities.push(
    'Seoul','Busan','Incheon','Daegu','Daejeon','Gwangju','Suwon','Ulsan',
    'Yongin','Goyang','Changwon','Seongnam','Hwaseong','Cheongju',
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['KOREA'] = countryCities;

    countryCities = [];
    countryCities.push(
    'Tokyo','Yokohama','Osaka','Nagoya','Sapporo','Fukuoka',
    'Kobe','Kawasaki','Kyoto','Saitama','Hiroshima','Sendai','Chiba','Kitakyushu'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['JAPAN'] = countryCities;

    countryCities = [];
    countryCities.push(
      'George Town', 'Kuala Lumpur','Ipoh','Kuching',
      'Johor Bahru','Kota Kinabula','Shah Alam','Malacca City','Alor Setar',
      'Miri','Petaling Jaya','Iskandar Puteri','Seberang Perai','Seremban',
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['MALAYSIA'] = countryCities;


    return map;
  }
}
