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

  currentUser: User | null;

  title: string | undefined;
  userIdToView: string | null;
  userToView: User | null;
  userToViewUsername: string | undefined;
  userToViewFollowers: number | undefined;
  userToViewFollowing: number | undefined;
  userToViewProfilePic: string | undefined;

  listings: Listing[];
  searchTerm: string;
  hasLoaded: boolean | undefined;

  constructor(
    private router: Router,
    public sessionService: SessionService,
    public listingService: ListingService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.currentUser = this.sessionService.getCurrentUser();

    this.userIdToView = this.activatedRoute.snapshot.paramMap.get('userId');
    if (this.userIdToView === '#') {
      //This is to handle the missing userId at the beginning
      this.userIdToView = this.currentUser.userId.toString();
    }

    // eslint-disable-next-line radix
    this.userService.getUser(parseInt(this.userIdToView)).subscribe({
      next: (response) => {
        this.userToView = response;
        console.log(this.userToView.userId);
        console.log(this.currentUser.userId);

        this.userToViewUsername = this.userToView.username;
        this.userToViewFollowers = this.userToView.followers.length;
        this.userToViewFollowing = this.userToView.following.length;
        this.userToViewProfilePic = this.userToView.profilePic;

        this.listingService.getUserListings(this.userToView).subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            this.listings = response;
            this.hasLoaded = true;
          },
          error: (error) => {
            console.log('getAllAvailableListings.ts:' + error);
          },
        });
      },
      error: (error) => {
        console.log('********** View User Profile.ts: ' + error);
      },
    });

    // this.userToViewFollowers = this.currentUser.followers.length;
    // this.userToViewFollowing = this.currentUser.following.length;
    // this.userToViewProfilePic = this.currentUser.profilePic;

    console.log(this.userToView);
    console.log(this.listings);
  }

  displayProfilePic() {
    return 'http://localhost:8080/Tompang-war' + this.currentUser.profilePic;
  }

  isProfileTheCurrentUser(): boolean {
    return this.userToView.userId === this.currentUser.userId;
  }

  displayFollowButton(): boolean {
    // return this.hasLoaded === true && !this.isProfileTheCurrentUser() &&
    // !this.currentUser.following.includes(this.userToView);

    return this.hasLoaded === true && !this.isProfileTheCurrentUser();
  }

  displayUnfollowButton(): boolean {
    // return this.hasLoaded === true && !this.isProfileTheCurrentUser() &&
    // this.currentUser.following.includes(this.userToView);

    return this.hasLoaded === true && !this.isProfileTheCurrentUser();
  }

  followUser() {
    //user to follow userToView
    console.log('Following user..');

    //Must update the current user to have the updated list of
    //following so that it will rerender the Unfollow button
  }

  unfollowUser() {
    //user to unfollow is userToView
    console.log('Unfollowing user..');

    //Must update the current user to have the updated list of
    //following so that it will rerender the Follow button
  }
}
