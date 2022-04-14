import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.page.html',
  styleUrls: ['./footer.page.scss'],
})
export class FooterPage implements OnInit {

  constructor(private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionService) { }

  ngOnInit() {
  }

  back()
	{
		this.location.back();
  }

  //Need to repeat this method in the Shop page as well.
  createListingPage() {
    console.log('Going to create listing page');
  }

  //Need to repeat this method in the Shop page as well.
  profilePage() {
    const currentUser = this.sessionService.getCurrentUser();
    this.router.navigate(['/profile/' + currentUser.userId]);
  }

}
