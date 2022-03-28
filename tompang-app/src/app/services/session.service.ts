import { Injectable } from '@angular/core';

import { User } from '../models/user';



@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() {}

  getIsLogin(): boolean {
		if(sessionStorage.isLogin === 'true')
		{
			return true;
		}
		else
		{
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
}
