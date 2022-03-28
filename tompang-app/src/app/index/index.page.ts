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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionService,
    private userService: UserService) { }

  ngOnInit() {
  }

  userLogin(): void {
    const user: User | null = this.userService.userLogin(this.username, this.password);

    if (user != null)
    {
      this.sessionService.setIsLogin(true);
      this.sessionService.setCurrentUser(user);
      this.loginError = false;

      this.router.navigate(['/shop']);
    }
    else
    {
      this.loginError = true;
    }
  }

}
