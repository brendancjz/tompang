import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../models/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-view-followers',
  templateUrl: './view-followers.page.html',
  styleUrls: ['./view-followers.page.scss'],
})
export class ViewFollowersPage implements OnInit {
  userIdToView: string | null;
  userToView: User | null;
  userFollowers: User[];

  searchTerm: string;

  constructor(private location: Location, private router: Router,
      private activatedRoute: ActivatedRoute,
      private userService: UserService) { }

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewFollowers');

    this.userIdToView = this.activatedRoute.snapshot.paramMap.get('userId');
    this.userFollowers = [];

    // eslint-disable-next-line radix
    this.userService.getUser(parseInt(this.userIdToView)).subscribe({
      next: (response) => {
        this.userToView = response;
        this.userFollowers = this.userToView.followers;
        console.log(this.userFollowers);

      },
      error: (error) => {
        console.log('********** View User Profile.ts: ' + error);
        this.location.back();
      },
    });
  }

}
