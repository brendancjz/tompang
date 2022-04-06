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

  editError: boolean;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService) {}

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});
  }

  updateUserPassword(): void {
    console.log('Changing password...');
    console.log('Current Password: ' + this.currentPassword);
    console.log('New Password: ' + this.newPassword);
    console.log('Repeat Password: ' + this.repeatPassword);

    

    const currentUser: User = this.sessionService.getCurrentUser();
    if (this.currentPassword === currentUser.password && this.newPassword === this.repeatPassword
      && this.newPassword !== this.currentPassword) {
        console.log('Good to change password');
        
        this.userService.updateUserPassword(this.newPassword).subscribe({
          next: (response) => {
            this.resultSuccess = true;
            this.resultError = false;
            this.message = ' User password updated successfully';
          },
          error: (error) => {
            this.resultError = true;
            this.resultSuccess = false;
            this.message =
              'An error has occurred while updating the user password: ' + error;
    
            console.log('********** UpdateUserPage: ' + error);
          },
        });
        this.editError = false;
      } else{
    this.editError = true;
      }
  }

  resetPage(): void {
    this.currentPassword = undefined;
    this.newPassword = undefined;
    this.repeatPassword = undefined;
    this.editError = false;
  }
}
