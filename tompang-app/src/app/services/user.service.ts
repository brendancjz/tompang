import { Injectable } from '@angular/core';

import { User } from '../models/user';



@Injectable({
  providedIn: 'root'
})
export class UserService
{
  users: User[];



  constructor()
  {
    this.users = new Array();

  }



  userLogin(username: string | undefined, password: string | undefined): User | null
	{
    //incomplete implementation.
		return new User();
	}
}
