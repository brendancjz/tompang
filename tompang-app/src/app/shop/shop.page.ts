import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';


@Component({
  selector: 'app-shop',
  templateUrl: './shop.page.html',
  styleUrls: ['./shop.page.scss'],
})
export class ShopPage implements OnInit {
  tompangLogo = '../../assets/images/tompang_logo_white.png';

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) { }

  ngOnInit() {
    this.checkUserLogin();
  }


  checkUserLogin(): void {
    console.log('Checking User Login Status..');
    if (!this.sessionService.getIsLogin()) {
      this.router.navigate(['/index']);
    }
  }
}
