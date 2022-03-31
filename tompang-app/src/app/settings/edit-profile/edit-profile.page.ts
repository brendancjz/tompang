import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.page.html',
  styleUrls: ['./edit-profile.page.scss'],
})
export class EditProfilePage implements OnInit {

  firstName: string | undefined;
  lastName: string | undefined;
  email: string | undefined;
  contactNumber: number | undefined;
  dateOfBirth: Date | undefined;

  editError: boolean;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {
      const currentUser = sessionService.getCurrentUser();

      this.firstName = currentUser.firstName;
      this.lastName = currentUser.lastName;
      this.email = currentUser.email;
      this.contactNumber = currentUser.contactNumber;
      this.dateOfBirth = currentUser.dateOfBirth;
    }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});
  }

  updateUser(): void {
    console.log('Updating user...');
    const currentUser = this.sessionService.getCurrentUser();
    let updatedUser = new User(currentUser.userId, this.firstName, this.lastName,
      currentUser.username, currentUser.password, this.email, this.dateOfBirth, currentUser.profilePic,
      this.contactNumber);

    updatedUser = this.userService.updateUser(updatedUser);
    //Update sessionServie currentUser
  }

  resetPage(): void {
    this.firstName = undefined;
    this.lastName = undefined;
    this.email = undefined;
    this.contactNumber = undefined;
    this.dateOfBirth = undefined;
    this.editError = false;
  }
}


