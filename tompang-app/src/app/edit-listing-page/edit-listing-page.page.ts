import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { ListingService } from '../services/listing.service';
import { UserService } from '../services/user.service';
import { Listing } from '../models/listing';

@Component({
  selector: 'app-edit-listing-page',
  templateUrl: './edit-listing-page.page.html',
  styleUrls: ['./edit-listing-page.page.scss'],
})
export class EditListingPagePage implements OnInit {
  listingId: string | null;
  listingToView: Listing | null;

  hasLoaded: boolean;
  retrieveListingError: boolean;

  categories: string[];
  countries: string[];
  cities: string[];
  countryCityMap = {};

  

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private userService: UserService
  ) {
    this.categories = [
      'FOOD',
      'APPAREL',
      'ACCESSORIES',
      'FOOTWEAR',
      'GIFTS',
      'ELECTRONICS',
    ];
    this.countries = ['Japan', 'Korea', 'Malaysia', 'Singapore', 'USA'];

    this.countryCityMap = this.initialiseCountriesCities();
  }

  ngOnInit() {
    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');

    if (this.listingId != null) {
      this.listingService
        .getListingByListingId(Number(this.listingId))
        .subscribe({
          next: (response) => {
            this.listingToView = response;
            this.hasLoaded = true;
          },
          error: (error) => {
            this.retrieveListingError = true;
            console.log('********** View Listing Details Page.ts: ' + error);
          },
        });
    }
  }

  initialiseCountriesCities() {
    const map = {};
    let countryCities = [];
    countryCities.push(
      'Alabama',
      'Alaska',
      'Arizona',
      'Arkansas',
      'California',
      'Colorado',
      'Connecticut',
      'Delaware',
      'Florida',
      'Georgia',
      'Hawaii',
      'Idaho',
      'Illinois',
      'Indiana',
      'Iowa',
      'Kansas',
      'Kentucky',
      'Louisiana',
      'Maine',
      'Maryland',
      'Massachusetts',
      'Michigan',
      'Minnesota',
      'Missouri',
      'Montana',
      'Nebraska',
      'Nevada',
      'New Hampshire',
      'New Jersey',
      'New Mexico',
      'New York',
      'North Carolina',
      'North Dakota',
      'Ohio',
      'Oklahoma',
      'Oregon',
      'Pennsylvania',
      'Rhode Island',
      'South Carolina',
      'South Dakota',
      'Tennessee',
      'Texas',
      'Utah',
      'Vermont',
      'Washington',
      'West Virginia',
      'Wisconsin',
      'Wyoming'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['USA'] = countryCities;

    countryCities = [];
    countryCities.push('Singapore');
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['Singapore'] = countryCities;

    countryCities = [];
    countryCities.push(
      'Seoul',
      'Busan',
      'Incheon',
      'Daegu',
      'Daejeon',
      'Gwangju',
      'Suwon',
      'Ulsan',
      'Yongin',
      'Goyang',
      'Changwon',
      'Seongnam',
      'Hwaseong',
      'Cheongju'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['Korea'] = countryCities;

    countryCities = [];
    countryCities.push(
      'Tokyo',
      'Yokohama',
      'Osaka',
      'Nagoya',
      'Sapporo',
      'Fukuoka',
      'Kobe',
      'Kawasaki',
      'Kyoto',
      'Saitama',
      'Hiroshima',
      'Sendai',
      'Chiba',
      'Kitakyushu'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['Japan'] = countryCities;

    countryCities = [];
    countryCities.push(
      'George Town',
      'Kuala Lumpur',
      'Ipoh',
      'Kuching',
      'Johor Bahru',
      'Kota Kinabula',
      'Shah Alam',
      'Malacca City',
      'Alor Setar',
      'Miri',
      'Petaling Jaya',
      'Iskandar Puteri',
      'Seberang Perai',
      'Seremban'
    );
    // eslint-disable-next-line @typescript-eslint/dot-notation
    map['Malaysia'] = countryCities;

    return map;
  }
}
