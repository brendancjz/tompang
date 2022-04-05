import { Injectable } from '@angular/core';

import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CreditCard } from '../models/creditCard';

import { User } from '../models/user';
import { SessionService } from './session.service';
import { catchError } from 'rxjs/operators';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class UserService {
  users: User[];
  baseUrl = '/api/User';

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {
    this.users = new Array();
  }

  getUser(username: string | undefined): Observable<User> {
    return this.httpClient
      .get<User>(this.baseUrl + '/retrieveUser?username=' + username)
      .pipe(catchError(this.handleError));
  }

  // userLogin(
  //   username: string | undefined,
  //   password: string | undefined): Observable<User> {
  //   return this.httpClient
  //     .get<User>(
  //       this.baseUrl +
  //         '/userLogin?username=' +
  //         username +
  //         '&password=' +
  //         password
  //     )
  //     .pipe(catchError(this.handleError));
  // }

  updateUser(updatedUser: User): User | null {
    //Call web service
    console.log('Updating user in UserService');
    return updatedUser;
  }

  getSampleUser() {
    const manager = new User(
      1,
      'Brendan',
      'Chia',
      'manager',
      'password',
      'brendan.chia@gmail.com',
      new Date(),
      '',
      84822514
    );

    manager.creditCards.push(
      new CreditCard(
        1,
        'DBS',
        'BRENDAN CHIA',
        4635123412341234,
        123,
        new Date()
      )
    );
    manager.creditCards.push(
      new CreditCard(
        2,
        'AMEX',
        'CHIA JUN ZHE',
        9876546723171247,
        125,
        new Date()
      )
    );

    return manager;
  }

  userLogin(
    username: string | undefined,
    password: string | undefined
  ): User | null {
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

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error;
    } else {
      errorMessage =
        'A HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error}`;
    }

    console.error(errorMessage);

    return throwError(() => new Error(errorMessage));
  }
}
