import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Listing } from '../models/listing';
import { FileUploadService } from '../services/fileUpload.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-create-listing',
  templateUrl: './create-listing.page.html',
  styleUrls: ['./create-listing.page.scss'],
})
export class CreateListingPage implements OnInit {
  @ViewChild('fileInput')
  fileInput: ElementRef;

  title: string | undefined;
  country: string | undefined;
  city: string | undefined;
  description: string | undefined;
  category: string | undefined;
  price: number | undefined;
  quantity: number | undefined;
  expectedArrivalDate: string | undefined;

  listingImages: string[] | undefined;
  newListingImage: File | null;
  imageSuccessMsg: string;
  imageSuccess: boolean;

  creationError: boolean;
  creationErrorMessage: string | undefined;
  creationSuccessful: boolean;

  categories: string[];
  countries: string[];
  cities: string[];
  countryCityMap = {};

  listingCreationSuccessful: boolean;

  constructor(
    private listingService: ListingService,
    private fileUploadService: FileUploadService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.listingImages = [];

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
          this.listingImages.push('/uploadedFiles/' + fileName);
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

  createListing(): void {
    console.log('Creating listing..');
    console.log(this.title);
    console.log(this.country);
    console.log(this.city);
    console.log(this.description);
    console.log(this.category);
    console.log(this.price);
    console.log(this.quantity);
    console.log(this.expectedArrivalDate);
    console.log(this.listingImages);

    this.doValidation();
    if (this.creationError) {
      return;
    }

    console.log('Passed validations. Creating listing...');
    const currentUser = this.sessionService.getCurrentUser();

    const listing: Listing = new Listing();
    listing.title = this.title;
    listing.country = this.country;
    listing.city = this.city;
    listing.description = this.description;
    listing.category = this.category.toUpperCase();
    listing.price = this.price;
    listing.quantity = this.quantity;
    listing.expectedArrivalDate = new Date(this.expectedArrivalDate);
    listing.photos = this.listingImages;
    listing.createdBy = currentUser;

    this.listingService.createListing(listing).subscribe({
      // eslint-disable-next-line @typescript-eslint/no-shadow
      next: (response) => {
        this.listingCreationSuccessful = true;
        this.resetInputs();
        console.log('Successful Creation', listing);
      },
      error: (error) => {
        console.log('Error in creating listing:' + error);
      },
    });
  }

  getPhotoUrl(photo: string) {
    return this.sessionService.getImageBaseUrl() + photo;
  }

  resetInputs(): void {
    this.title = undefined;
    this.country = undefined;
    this.city = undefined;
    this.description = undefined;
    this.category = undefined;
    this.price = undefined;
    this.quantity = undefined;
    this.listingImages = [];
    this.imageSuccess = undefined;
  }

  doValidation(): void {
    this.creationError = false;

    if (
      this.title === undefined ||
      this.country === undefined ||
      this.city === undefined ||
      this.description === undefined ||
      this.category === undefined ||
      this.price === undefined ||
      this.quantity === undefined ||
      this.listingImages.length === 0
    ) {
      this.creationError = true;
      this.creationErrorMessage =
        'Incomplete fields, could not create listing.';
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

    if (this.description.length <= 5) {
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
