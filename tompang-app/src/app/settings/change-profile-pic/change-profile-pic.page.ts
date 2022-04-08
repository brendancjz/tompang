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

  basePicUrl = 'picApi';
  currentProfilePic: string;
  newProfilePic: string | undefined;

  editError: boolean;

  currentUser: User;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
  }

  displayProfilePic() {
    return 'http://localhost:8080/Tompang-war' + this.currentUser.profilePic;
  }

  updateUserProfilePic(): void {
    console.log('Updating user profile pic...');

  }

  resetPage(): void {
    this.newProfilePic = undefined;
    this.editError = false;
  }
}
