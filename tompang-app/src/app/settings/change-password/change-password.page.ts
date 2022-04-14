import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.page.html',
  styleUrls: ['./change-password.page.scss'],
})
export class ChangePasswordPage implements OnInit {

  currentPassword: string | undefined;
  newPassword: string | undefined;
  repeatPassword: string | undefined;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  currentUser: User;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {}

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
    this.currentPassword = undefined;
    this.newPassword = undefined;
    this.repeatPassword = undefined;
    this.resultSuccess = false;
  }

  ionViewWillEnter() {}

  updateUserPassword(): void {
    this.resultError = false;
    this.doValidation();
    if(this.resultError) {
      console.log('Validation error ' + this.message);
      return;
    }

    console.log('Validation passed.');
    this.userService.updateUserPassword(this.newPassword).subscribe({
      next: (response) => {
        console.log('Update password successful');
        this.resultSuccess = true;
        this.resultError = false;
        this.message = 'User password updated successfully';

        this.currentUser.password = this.newPassword;
        this.sessionService.setCurrentUser(this.currentUser);
        this.resetPage();
      },
      error: (error) => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = 'Invalid update: Unexpected error occured. Try again later';
        this.resetPage();
        console.log('********** UpdateUserPage: ' + error);
      },
    });

  }

  doValidation(): void {
    if (this.currentPassword === undefined || this.newPassword === undefined ||
      this.repeatPassword === undefined) {
        this.resultError = true;
        this.message = 'Invalid update: Missing input fields';
        return;
    }

    console.log(this.currentUser.password);
    if (this.currentPassword !== this.currentUser.password) {
      this.resultError = true;
      this.message = 'Invalid update: Current Password does not match';
      return;
    }

    if (this.newPassword !== this.repeatPassword) {
      this.resultError = true;
      this.message = 'Invalid update: Passwords do not match';
      return;
    }
  }

  resetPage(): void {
    this.currentPassword = undefined;
    this.newPassword = undefined;
    this.repeatPassword = undefined;
    this.resultError = undefined;
  }
}
