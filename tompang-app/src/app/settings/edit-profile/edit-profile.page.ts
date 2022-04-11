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
  firstName: string;
  lastName: string;
  email: string;
  contactNumber: number;
  dateOfBirth: string;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(
    public sessionService: SessionService,
    private userService: UserService
  ) {}

  ngOnInit() {
    document.getElementById('back-button').addEventListener(
      'click',
      () => {
        this.resetPage();
      },
      { once: true }
    );
  }

  ionViewWillEnter() {
    const currentUser = this.sessionService.getCurrentUser();

    this.firstName = currentUser.firstName;
    this.lastName = currentUser.lastName;
    this.email = currentUser.email;
    this.contactNumber = currentUser.contactNumber;
    const dobString = currentUser.dateOfBirth.toString().split('T')[0];
    this.dateOfBirth = dobString + 'T00:01:00-04:00';
  }

  updateUser(): void {
    console.log('Updating user...');
    const currentUser = this.sessionService.getCurrentUser();

    this.resultError = false;
    this.doValidation();
    if (this.resultError) {
      console.log('Validation error');
      return;
    }

    console.log('firstname: ' + this.firstName);
    console.log('lastname: ' + this.lastName);
    console.log('email: ' + typeof(this.email));
    console.log('contact number: ' + this.contactNumber);
    console.log('dob: ' + this.dateOfBirth);

    const updatedUser = new User(
      currentUser.userId,
      this.firstName,
      this.lastName,
      currentUser.username,
      currentUser.password,
      this.email,
      new Date(this.dateOfBirth),
      currentUser.profilePic,
      this.contactNumber
    );

    this.userService.updateUser(updatedUser).subscribe({
      next: (response) => {
        console.log('Update user success!');
        this.resultSuccess = true;
        this.resultError = false;
        this.message = 'User updated successfully';

      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'Invalid edit: Unexpected error occured. Try again later.';

        console.log('********** UpdateUserPage: ' + error);
      },
    });
  }

  doValidation(): void {
    if (this.firstName.length === 0 || this.lastName.length === 0 || this.email.length === 0 ||
      this.contactNumber.toString().length === 0) {
        this.resultError = true;
        this.message = 'Invalid edit: Missing input fields';
        return;
    }

    if (this.contactNumber.toString().length !== 8) {
      this.resultError = true;
      this.message = 'Invalid edit: Incorrect formatting of Contact Number';
      return;
    }

    if (!this.email.includes('@') || !this.email.includes('.com')) {
      this.resultError = true;
      this.message = 'Invalid edit: Incorrect formatting of Email';
      return;
    }

  }

  resetPage(): void {
    this.firstName = undefined;
    this.lastName = undefined;
    this.email = undefined;
    this.contactNumber = undefined;
    this.dateOfBirth = undefined;
    this.resultError = undefined;
    this.resultSuccess = undefined;
    this.message = undefined;

  }

  confirm() {
    console.log('confirmed');
  }
}
