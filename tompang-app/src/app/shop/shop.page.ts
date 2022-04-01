import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';


@Component({
  selector: 'app-shop',
  templateUrl: './shop.page.html',
  styleUrls: ['./shop.page.scss'],
})
export class ShopPage implements OnInit {

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) { }

  ngOnInit() {
  }

  //Need to repeat this method in the Footer page as well.
  createListingPage() {
    console.log('Going to create listing page');
  }

  //Need to repeat this method in the Footer page as well.
  profilePage() {
    this.router.navigate(['/profile']);
  }
}
