import { Component, OnInit } from '@angular/core';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';
  defaultProfilePic = '../../assets/images/uploadedFiles/default_picture.jpg';

  title: string | undefined;
  currentUser: User;
  username: string | undefined;

  myListings: Listing[];
  searchTerm: string;

  constructor(
    public sessionService: SessionService,
    public listingService: ListingService
  ) {

    this.currentUser = sessionService.getCurrentUser();
    this.username = this.currentUser.username;

    this.myListings = listingService.getUserListings(this.currentUser);

  }

  ngOnInit() {}

}
