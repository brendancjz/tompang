import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../models/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-view-following',
  templateUrl: './view-following.page.html',
  styleUrls: ['./view-following.page.scss'],
})
export class ViewFollowingPage implements OnInit {

  userIdToView: string | null;
  userToView: User | null;
  userFollowing: User[];

  searchTerm: string;

  constructor(private router: Router,
      private activatedRoute: ActivatedRoute,
      private userService: UserService) { }

  ngOnInit() {}

  ionViewWillEnter() {
    console.log('ionViewWillEnter ViewFollowing');

    this.userIdToView = this.activatedRoute.snapshot.paramMap.get('userId');
    this.userFollowing = [];

    // eslint-disable-next-line radix
    this.userService.getUser(parseInt(this.userIdToView)).subscribe({
      next: (response) => {
        this.userToView = response;
        this.userFollowing = this.userToView.following;
        console.log('User Following ', this.userFollowing);

      },
      error: (error) => {
        console.log('********** View User Profile.ts: ' + error);
      },
    });
  }

}
