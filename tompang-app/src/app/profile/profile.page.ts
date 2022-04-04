import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  samplePic = '../../assets/images/tompang_icon_logo_blue.png';
  defaultProfilePic = '../../assets/images/uploadedFiles/default_picture.jpg';

  currentUser: User | null;

  title: string | undefined;
  userId: string | null;
  userToView: User | null;
  userToViewUsername: string | undefined;

  listings: Listing[];
  searchTerm: string;

  constructor(
    private router: Router,
    public sessionService: SessionService,
    public listingService: ListingService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {

    this.currentUser = this.sessionService.getCurrentUser();

    this.userId = this.activatedRoute.snapshot.paramMap.get('userId');
    if (this.userId === '#') { //This is to handle the missing userId at the beginning
      this.userId = this.currentUser.userId.toString();
    }

    // eslint-disable-next-line radix
    this.userToView = this.userService.getUserByUserId(parseInt(this.userId));
    this.userToViewUsername = this.userToView.username;
    this.listings = this.listingService.getUserListings(this.userToView);


  }

  isProfileTheCurrentUser(): boolean {
    return this.userToView.username === this.currentUser.username;
  }

}
