import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Listing } from 'src/app/models/listing';
import { User } from 'src/app/models/user';
import { ListingService } from 'src/app/services/listing.service';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.page.html',
  styleUrls: ['./listing-card.page.scss'],
})
export class ListingCardPage implements OnInit {
  currentUser: User;

  listingIsLiked: boolean;

  // eslint-disable-next-line @typescript-eslint/member-ordering
  @Input() listing: Listing;

  constructor(
    private router: Router,
    public listingService: ListingService,
    private sessionService: SessionService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.currentUser = this.sessionService.getCurrentUser();
    this.checkLikedByUser();
  }

  viewListingDetails(listing: Listing): void {
    console.log(listing.title);
    console.log(listing.listingId);
    this.router.navigate(['/view-listing-details/' + listing.listingId]);
  }

  //Methods for Listings list
  getListingFirstPhoto(listing: Listing): string {
    const photoUrl = this.sessionService.getImageBaseUrl() + listing.photos[0];
    return photoUrl;
  }

  formatListingTitle(listing: Listing): string {
    const maxNumberBeforeCutOff = 15;
    if (listing.title.length >= maxNumberBeforeCutOff) {
      return listing.title.substring(0, maxNumberBeforeCutOff - 3) + '...';
    }

    return listing.title;
  }

  formatListingCreatedBy(listing: Listing): string {
    const maxNumberBeforeCutOff = 12;

    // to display current user's listing's createdBy name since its unmarshalled on RWS side
    if (listing.createdBy == null) {
      return this.currentUser.username;
    }
    if (listing.createdBy.username.length >= maxNumberBeforeCutOff) {
      return (
        '@' +
        listing.createdBy.username.substring(0, maxNumberBeforeCutOff - 3) +
        '...'
      );
    }

    return '@' + listing.createdBy.username;
  }

  likeListing(listing: Listing): void {
    this.listingService.likeListing(listing).subscribe({
      next: (response) => {
        this.listingService.getListingByListingId(this.listing.listingId);
        console.log('listing liked!');
      },
      error: (error) => {
        console.log('view-listing-card.page.ts:' + error);
        console.log('FAIL');
      },
    });
    this.listingIsLiked = true;
    this.listing.likedByUsers.length += 1;
  }

  unlikeListing(listing: Listing): void {
    this.listingService.unlikeListing(listing).subscribe({
      next: (response) => {
        this.listingService.getListingByListingId(this.listing.listingId);
        console.log('listing unliked!');
      },
      error: (error) => {
        console.log('view-listing-card.page.ts:' + error);
        console.log('FAIL');
      },
    });
    this.listingIsLiked = false;
    this.listing.likedByUsers.length -= 1;
  }

  checkLikedByUser(): void {
    this.listing.likedByUsers.map((likedUser) => {
      if (this.currentUser.userId === likedUser.userId) {
        this.listingIsLiked = true;
      }
    });
  }
}
