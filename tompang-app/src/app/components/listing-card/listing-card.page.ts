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

  basePictureUrl = '../../assets/images';
  currentUser: User;

  listingIsLiked: boolean;

  // eslint-disable-next-line @typescript-eslint/member-ordering
  @Input() listing: Listing;

  constructor(private router: Router, public listingService: ListingService,
    private sessionSerivce: SessionService,
    private userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.sessionSerivce.getCurrentUser();
    this.listingIsLiked = this.doesCurrentUserLikeThisListing();
  }

  viewListingDetails(listing: Listing): void {
    this.router.navigate(['/view-listing-details/' + listing.listingId]);
  }

  //Methods for Listings list
  getListingFirstPhoto(listing: Listing): string {
    const photoUrl = this.basePictureUrl + listing.photos[0];
    return photoUrl;
  }

  formatListingTitle(listing: Listing): string {
    const maxNumberBeforeCutOff = 15;
    if (listing.title.length >= maxNumberBeforeCutOff) {
      return listing.title.substring(0,maxNumberBeforeCutOff - 3) + '...';
    }

    return listing.title;
  }

  formatListingCreatedBy(listing: Listing): string {
    //TODO: When User can be parsed through
    // const maxNumberBeforeCutOff = 12;
    // if (listing.createdBy.username.length >= maxNumberBeforeCutOff) {
    //   return '@' + listing.createdBy.username.substring(0,maxNumberBeforeCutOff - 3) + '...';
    // }

    // return '@' + listing.createdBy;
    return '@stackoverflow';
  }

  likeListing(listing: Listing): void {
    this.listingService.likeListing(listing);
    this.listingIsLiked = true;
  }

  unlikeListing(listing: Listing): void {
    this.listingService.unlikeListing(listing);
    this.listingIsLiked = false;
  }

  doesCurrentUserLikeThisListing(): boolean {
    return this.userService.isListingLikedByUser(this.currentUser.userId, this.listing.listingId);
  }
}
