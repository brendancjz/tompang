import { Injectable } from '@angular/core';
import { Listing } from '../models/listing';

import { User } from '../models/user';
import { ListingService } from './listing.service';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  constructor() {}

  getIsLogin(): boolean {
    if (sessionStorage.isLogin === 'true') {
      return true;
    } else {
      return false;
    }
  }

  setIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin;
  }

  getCurrentUser(): User {
    return JSON.parse(sessionStorage.currentUser);
  }

  setCurrentUser(currentUser: User | null): void {
    sessionStorage.currentUser = JSON.stringify(currentUser);
  }

  getUsername(): string {
    return sessionStorage['username'];
  }

  setUsername(username: string | undefined): void {
    sessionStorage['username'] = username;
  }

  getListing(): Listing {
    return sessionStorage['listing1'];
  }

  setListing(listing: Listing | undefined): void {
    sessionStorage['listing1'] = listing;
  }
}
