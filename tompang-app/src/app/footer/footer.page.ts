import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.page.html',
  styleUrls: ['./footer.page.scss'],
})
export class FooterPage implements OnInit {

  constructor(private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
  }

  back()
	{
		this.location.back();
    // this.resetPage();
  }

  createListingPage() {
    console.log('Going to create listing page');
  }

  profilePage() {
    this.router.navigate(['/profile']);
  }

}
