import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-change-profile-pic',
  templateUrl: './change-profile-pic.page.html',
  styleUrls: ['./change-profile-pic.page.scss'],
})
export class ChangeProfilePicPage implements OnInit {

  newProfilePic: string | undefined;

  editError: boolean;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) { }

  ngOnInit() {
  }

  updateUserProfilePic(): void {
    console.log('Updating user profile pic...');

  }
  back()
    {
      this.location.back();
      this.resetPage();
    }

  resetPage(): void {
    this.newProfilePic = undefined;
    this.editError = false;
  }
}
