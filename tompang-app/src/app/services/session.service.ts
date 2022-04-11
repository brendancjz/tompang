import { Injectable } from '@angular/core';
import { IP_ADDRESS } from 'src/environments/environment.prod';

import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  ipAddressBren = IP_ADDRESS;

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

  getImageBaseUrl() {
    //Change ip address
    return 'http://' + this.ipAddressBren + ':8080/Tompang-war/';
  }
}
