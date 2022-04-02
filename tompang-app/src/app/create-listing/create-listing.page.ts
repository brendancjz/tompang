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
      'JAPAN',
      'KOREA',
      'MALAYSIA',
      'SINGAPORE',
      'USA'
    ];

    this.countryCityMap = this.initialiseCountriesCities();
  }

  ngOnInit() {}

  createListing(): void {
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
