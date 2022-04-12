import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Listing } from '../models/listing';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-users-like-listing',
  templateUrl: './view-users-like-listing.page.html',
  styleUrls: ['./view-users-like-listing.page.scss'],
})
export class ViewUsersLikeListingPage implements OnInit {

  listingId: string | null;
  listingToView: Listing | null;
  retrieveListingError: boolean;

  hasLoaded: boolean;

  currentUser: User;

  searchTerm: string;

  constructor(private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,) { }

  ngOnInit() {
  }

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewUsersLikeListing');
    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');
    this.currentUser = this.sessionService.getCurrentUser();

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
            console.log('********** ViewUsersLikeListing Page.ts: ' + error);
            this.location.back();
          },
        });
    }
  }

}
