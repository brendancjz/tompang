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

  updateUser(updatedUser: User): User | null {
    //Call web service
    console.log('Updating user in UserService');
    return updatedUser;
  }

  userLogin(username: string | undefined, password: string | undefined): User | null
	{
    //incomplete implementation.
    const manager = new User(1, 'Brendan', 'Chia', 'manager', 'password', 'brendan.chia@gmail.com', new Date(), '', 84822514);
		if (manager.username === username && manager.password === password) {
      return manager;
    } else {
      return null;
    }
	}
}
