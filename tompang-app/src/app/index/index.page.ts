import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';
import { ListingService } from '../services/listing.service';
import { Listing } from '../models/listing';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-index',
  templateUrl: './index.page.html',
  styleUrls: ['./index.page.scss'],
})
export class IndexPage implements OnInit {
  tompangLogo = '../../assets/images/tompang_logo_white.png';

  //Login Box
  username: string | undefined;
  password: string | undefined;
  loginError: boolean;

  //Register Box
  firstName: string | undefined;
  lastName: string | undefined;
  registerUsername: string | undefined;
  registerPassword: string | undefined;
  registerRepeatPassword: string | undefined;
  email: string | undefined;
  dateOfBirth: Date | undefined;
  contactNumber: number | undefined;
  registrationError: boolean;
  registrationSuccess: boolean;
  registrationErrorMsg: string | undefined;

  //Views
  possibleViews = ['DEFAULT', 'LOGIN', 'REGISTER'];
  currentView: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private userService: UserService,
    private listingService: ListingService
  ) {}

  ngOnInit() {
    this.currentView = this.possibleViews[0];
  }

  doRegisterUser(): void {
    if (
      this.firstName === undefined ||
      this.lastName === undefined ||
      this.registerUsername === undefined ||
      this.registerPassword === undefined ||
      this.registerRepeatPassword === undefined ||
      this.email === undefined ||
      this.dateOfBirth === undefined ||
      this.contactNumber === undefined
    ) {
      this.registrationError = true;
      this.registrationErrorMsg =
        'Invalid registration: Incomplete registration!';
      return;
    }
    if (
      this.registerPassword !== undefined &&
      this.registerRepeatPassword !== undefined &&
      this.registerPassword !== this.registerRepeatPassword
    ) {
      this.registrationError = true;
      this.registrationErrorMsg =
        'Invalid registration: Passwords do not match!';
      return;
    }

    const user: User = new User();
    user.firstName = this.firstName;
    user.lastName = this.lastName;
    user.username = this.registerUsername;
    user.password = this.registerPassword;
    user.email = this.email;
    user.dateOfBirth = this.dateOfBirth;
    user.profilePic = '/uploadedFiles/default_picture.jpg';
    user.contactNumber = this.contactNumber;
    user.joinedOn = new Date();
    user.isAdmin = false;
    user.isDisabled = false;

    console.log('Registered User', user);

    this.userService.createUser(user).subscribe({
      next:(response)=>{
        const newUserId: number = response;
        this.registrationSuccess = true;
        this.registrationError = false;
        this.registrationErrorMsg = 'Successfully created an account';
      },
      error:(error)=>{
        this.registrationError = true;
        this.registrationSuccess = false;
        this.registrationErrorMsg = 'Unexpected error occurred. Try again later.';

        console.log(this.registrationErrorMsg);
        console.log('********** CreateNewUserPage: ' + error);
      }
    });
    this.resetPage();
  }

  doLogin(): void {
    console.log('User logging in...');

    this.userService.userLogin(this.username, this.password).subscribe({
      next: (response) => {
        const user: User = response;

        if (user != null) {
          this.sessionService.setIsLogin(true);
          this.sessionService.setCurrentUser(user);
          this.loginError = false;
          this.resetPage();

          this.router.navigate(['/shop']); //After successful login, direct to Shop
        } else {
          this.loginError = true;
        }
      },
    });
  }

  doLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentUser(null);

    this.router.navigate(['/index']);
  }

  resetPage(): void {
    //Login Box
    this.username = undefined;
    this.password = undefined;
    this.loginError = undefined;

    //Register Box
    this.firstName = undefined;
    this.lastName = undefined;
    this.registerUsername = undefined;
    this.registerPassword = undefined;
    this.registerRepeatPassword = undefined;
    this.email = undefined;
    this.dateOfBirth = undefined;
    this.contactNumber = undefined;
    this.registrationError = undefined;
    this.registrationErrorMsg = undefined;
  }

  displayLoginBox(): void {
    this.currentView = this.possibleViews[1];
  }

  hideLoginBox(): void {
    this.currentView = this.possibleViews[0];
    this.resetPage();
  }

  displayRegisterBox(): void {
    this.currentView = this.possibleViews[2];
  }

  hideRegisterBox(): void {
    this.currentView = this.possibleViews[0];
    this.resetPage();
  }
}
