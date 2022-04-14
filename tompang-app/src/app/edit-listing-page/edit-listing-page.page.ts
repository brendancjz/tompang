import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { ListingService } from '../services/listing.service';
import { UserService } from '../services/user.service';
import { Listing } from '../models/listing';
import { FileUploadService } from '../services/fileUpload.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-edit-listing-page',
  templateUrl: './edit-listing-page.page.html',
  styleUrls: ['./edit-listing-page.page.scss'],
})
export class EditListingPagePage implements OnInit {
  @ViewChild('fileInput')
  fileInput: ElementRef;

  listingId: string | null;
  listingToView: Listing | null;
  updatedExpectedArrivalDate: string | null;

  hasLoaded: boolean;
  retrieveListingError: boolean;

  categories: string[];
  countries: string[];
  cities: string[];
  countryCityMap = {};

  updateError: boolean;
  updateErrorMsg: string;

  newListingImage: File | null;
  imageSuccessMsg: string;
  imageSuccess: boolean;

  listingUpdateSuccessful: boolean;

  constructor(
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private userService: UserService,
    private fileUploadService: FileUploadService
  ) {}

  ngOnInit() {
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

  ionViewWillEnter() {
    console.log('IonViewWillEnter EditListing');

    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');

    if (this.listingId != null) {
      this.listingService
        .getListingByListingId(Number(this.listingId))
        .subscribe({
          next: (response) => {
            this.listingToView = response;
            const eta = this.listingToView.expectedArrivalDate.toString().split('T')[0];
            this.updatedExpectedArrivalDate = eta + 'T00:01:00-04:00';
            this.cities = this.countryCityMap[this.listingToView.country];
            this.hasLoaded = true;
          },
          error: (error) => {
            this.retrieveListingError = true;
            console.log('********** View Listing Details Page.ts: ' + error);
            this.location.back();
          },
        });
    }
  }

  addingListingImage(event: any) {
    this.newListingImage = event.target.files.item(0);
  }

  addListingImage() {
    console.log(this.newListingImage);
    console.log(this.newListingImage.name);
    if (this.newListingImage != null) {
      const fileName = this.newListingImage.name;
      console.log(fileName);

      this.fileUploadService.uploadFile(this.newListingImage).subscribe({
        next: (response) => {
          console.log(fileName);
          console.log(
            '********** FileUploadComponent.ts: File uploaded successfully: ' +
              response.status
          );

          //Updating User Listing Images
          this.listingToView.photos.push('/uploadedFiles/' + fileName);
          this.imageSuccess = true;
          this.imageSuccessMsg = 'Added Image ' + fileName;
          this.resetFileInput();
        },
        error: (error) => {
          console.log('********** FileUploadComponent.ts: ' + error);
          console.log(error);
        },
      });
    }
  }

  resetFileInput() {
    console.log('Reseting file input');
    this.fileInput.nativeElement.value = '';
  }

  removeImage(photo: string) {
    const index = this.listingToView.photos.indexOf(photo);
    if (index > -1) {
      this.listingToView.photos.splice(index, 1);
    }
  }

  doUpdateListingDetails(): void {
    console.log('Update listing..');

    this.doValidation();
    if (this.updateError) {
      return;
    }

    this.listingToView.expectedArrivalDate = new Date(this.updatedExpectedArrivalDate);

    console.log('Before updating', this.listingToView);
    this.listingService.updateListing(this.listingToView).subscribe({
      // eslint-disable-next-line @typescript-eslint/no-shadow
      next: (response) => {
        this.listingUpdateSuccessful = true;
        console.log('Successful Update', this.listingToView);
      },
      error: (error) => {
        console.log('Error in creating listing:' + error);
      },
    });
  }

  getPhotoUrl(photo: string) {
    return this.sessionService.getImageBaseUrl() + photo;
  }

  doValidation(): void {
    this.updateError = false;

    if (
      this.listingToView.title.length === 0 ||
      this.listingToView.country.length === 0 ||
      this.listingToView.city.length === 0 ||
      this.listingToView.description.length === 0 ||
      this.listingToView.category.length === 0 ||
      this.listingToView.photos.length === 0
    ) {
      this.updateError = true;
      this.updateErrorMsg =
        'Incomplete fields, could not create listing.';
      return;
    }

    if (this.listingToView.price <= 0) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, Price cannot be negative or zero.';
      return;
    }

    if (this.listingToView.quantity <= 0) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, Quantity cannot be negative or zero';
      return;
    }

    if (this.listingToView.description.length <= 5) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, please add more description.';
      return;
    }

    if (this.listingToView.description.length >= 500) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, Description has exceeded word limit.';
      return;
    }

    if (this.listingToView.title.length <= 5) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, please add more to your title.';
      return;
    }

    if (this.listingToView.title.length >= 50) {
      this.updateError = true;
      this.updateErrorMsg = 'Sorry, Title has exceeded word limit.';
      return;
    }
  }

  selectCitiesFromCountry(country: string) {
    console.log('Changing cities');
    this.cities = this.countryCityMap[country];
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
