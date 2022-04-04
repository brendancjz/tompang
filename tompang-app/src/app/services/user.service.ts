import { Injectable } from '@angular/core';
import { CreditCard } from '../models/creditCard';

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

  getSampleUser() {
    const manager = new User(1, 'Brendan', 'Chia', 'manager', 'password', 'brendan.chia@gmail.com', new Date(), '', 84822514);

    manager.creditCards.push(new CreditCard(1, 'DBS','BRENDAN CHIA', 4635123412341234,123, new Date()));
    manager.creditCards.push(new CreditCard(2, 'AMEX','CHIA JUN ZHE', 9876546723171247,125, new Date()));

    return manager;
  }

  userLogin(username: string | undefined, password: string | undefined): User | null
	{
    //incomplete implementation.
    const manager = this.getSampleUser();
    if (manager.username === username && manager.password === password) {
      return manager;
    } else {
      return null;
    }
	}

  getUserByUserId(userId: number) {
    //To implement
    return this.getSampleUser();

  }

  isListingLikedByUser(userId: number, listingId: number) {
    //To implement
    return true;
  }
}
