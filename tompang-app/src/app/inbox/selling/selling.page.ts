import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-selling',
  templateUrl: './selling.page.html',
  styleUrls: ['./selling.page.scss'],
})
export class SellingPage implements OnInit {

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {}

  ngOnInit() {
  }

  back()
	{
		this.location.back();
    // this.resetPage();
  }
}
