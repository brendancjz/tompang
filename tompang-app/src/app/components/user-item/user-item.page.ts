import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.page.html',
  styleUrls: ['./user-item.page.scss'],
})
export class UserItemPage implements OnInit {

  @Input() user: User;

  constructor(private router: Router,
    public sessionService: SessionService) { }

  ngOnInit() {
  }

  getUserProfilePic(): string {
    return this.sessionService.getImageBaseUrl() + this.user.profilePic;
  }

  viewUser(user: User) {
    this.router.navigate(['/profile/' + this.user.userId]);
  }
}
