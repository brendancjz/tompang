import { Component, OnInit } from '@angular/core';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  title: string | undefined;

  constructor(
    public sessionService: SessionService,
    private listingService: ListingService
  ) {
    const listing = sessionService.getListing();

    this.title = listing.title;
  }

  ngOnInit() {}
}
