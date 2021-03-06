import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { SessionService } from '../services/session.service';

import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.page.html',
  styleUrls: ['./header.page.scss'],
})
export class HeaderPage implements OnInit {

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) {
    this.checkUserLogin();
   }

   ngOnInit() {
  }


  checkUserLogin(): void {
    console.log('Checking User Login Status..');
    if (!this.sessionService.getIsLogin()) {
      this.router.navigate(['/index']);
    }
  }
}
