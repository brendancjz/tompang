import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';

@Component({
  selector: 'app-index',
  templateUrl: './index.page.html',
  styleUrls: ['./index.page.scss'],
})
export class IndexPage implements OnInit {

  username: string | undefined;
  password: string | undefined;
  loginError: boolean;
  showLoginBox: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private userService: UserService) { }

  ngOnInit() {
    this.showLoginBox = false;
  }

  userLogin(): void {
    const user: User | null = this.userService.userLogin(this.username, this.password);

    if (user != null)
    {
      this.sessionService.setIsLogin(true);
      this.sessionService.setCurrentUser(user);
      this.loginError = false;
      this.showLoginBox = false; //Hide the login box.
      this.username = null;
      this.password = null;

      this.router.navigate(['/shop']); //After successful login, direct to Shop
    }
    else
    {
      this.loginError = true;
    }
  }

  userLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentUser(null);

    this.router.navigate(['/index']);
  }

  displayLoginBox(): void {
    this.showLoginBox = true;

  }

}
