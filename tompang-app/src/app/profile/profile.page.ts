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
  showFollowButton: boolean | undefined;

  constructor(
    private router: Router,
    public sessionService: SessionService,
    public listingService: ListingService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.currentUser = this.sessionService.getCurrentUser();

    console.log(this.currentUser);

    this.userIdToView = this.activatedRoute.snapshot.paramMap.get('userId');
    if (this.userIdToView === '#') {
      //This is to handle the missing userId at the beginning
      this.userIdToView = this.currentUser.userId.toString();
    }

    // eslint-disable-next-line radix
    this.userService.getUser(parseInt(this.userIdToView)).subscribe({
      next: (response) => {
        this.userToView = response;
        console.log(this.userToView);

        this.userToViewUsername = this.userToView.username;
        this.userToViewFollowers = this.userToView.followers.length;
        this.userToViewFollowing = this.userToView.following.length;
        this.userToViewProfilePic = this.userToView.profilePic;
        console.log(this.userToView);

        this.listingService.getUserListings(this.userToView).subscribe({
          // eslint-disable-next-line @typescript-eslint/no-shadow
          next: (response) => {
            this.listings = response;
            this.hasLoaded = true;
          },
          error: (error) => {
            this.listings = [];
            this.hasLoaded = true;
            console.log('getAllAvailableListings.ts:' + error);
          },
        });

        this.showFollowButton = true;

        this.currentUser.following.map((following) => {
          if (following.userId === this.userToView.userId) {
            this.showFollowButton = false;
          }
        });

        console.log('follow button' + this.showFollowButton);
      },
      error: (error) => {
        console.log('********** View User Profile.ts: ' + error);
      },
    });

    console.log(this.userToView);
    console.log(this.listings);
  }

  displayProfilePic() {
    return this.sessionService.getImageBaseUrl() + this.userToView.profilePic;
  }

  isProfileTheCurrentUser(): boolean {
    return this.userToView.userId === this.currentUser.userId;
  }

  followUser(userId: number) {
    //user to follow userToView
    console.log('Following user..');

    this.userService.follow(userId).subscribe({
      next: (response) => {
        console.log('user followed');
      },
    });
    this.userToViewFollowers += 1;
    this.showFollowButton = false;
    //Must update the current user to have the updated list of
    //following so that it will rerender the Unfollow button
  }

  unfollowUser(userId: number) {
    //user to unfollow is userToView
    console.log('Unfollowing user..');

    this.userService.unfollow(userId).subscribe({
      next: (response) => {
        console.log('user unfollowed');
      },
    });

    this.userToViewFollowers -= 1;
    this.showFollowButton = true;
    //Must update the current user to have the updated list of
    //following so that it will rerender the Follow button
  }

  viewFollowers() {
    console.log('view followers..');
    this.router.navigate(['/view-followers/' + this.userToView.userId]);

  }

  viewFollowing() {
    console.log('view following...');
    this.router.navigate(['/view-following/' + this.userToView.userId]);
  }
}
