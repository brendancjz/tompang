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

  constructor(public sessionService: SessionService,
    private userService: UserService) {}

  ngOnInit() {
    const currentUser = this.sessionService.getCurrentUser();

    this.firstName = currentUser.firstName;
    this.lastName = currentUser.lastName;
    this.email = currentUser.email;
    this.contactNumber = currentUser.contactNumber;
    const dobString = currentUser.dateOfBirth.toString().split('T')[0];
    this.dateOfBirth = new Date(dobString);

    console.log(new Date(dobString));
    console.log(this.dateOfBirth);


    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});
  }

  updateUser(): void {
    console.log('Updating user...');
    console.log('First Name: ' + this.firstName);
    console.log('Last Name: ' + this.lastName);
    console.log('Email: ' + this.email);
    console.log('Contact No.: ' + this.contactNumber);
    console.log('DoB: ' + this.dateOfBirth);

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


