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
    const currentUser: User = this.sessionService.getCurrentUser();
    if (this.currentPassword === currentUser.password && this.newPassword === this.repeatPassword
      && this.newPassword !== this.currentPassword) {
        console.log('Good to change password');

        //Update sessionServie currentUser
      }
    this.editError = true;
  }

  resetPage(): void {
    this.currentPassword = undefined;
    this.newPassword = undefined;
    this.repeatPassword = undefined;
    this.editError = false;
  }
}
