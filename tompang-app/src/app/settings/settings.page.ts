import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
})
export class SettingsPage implements OnInit {


  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) { }

  ngOnInit() {}

  hideSettingTabs() {
    console.log('Hiding Setting Tabs...');
    document.getElementById('transparent-tab').style.display = 'none';
    document.getElementById('edit-profile-tab').style.display = 'none';
    document.getElementById('change-profile-pic-tab').style.display = 'none';
    document.getElementById('change-password-tab').style.display = 'none';
    document.getElementById('manage-credit-cards-tab').style.display = 'none';
    document.getElementById('logout-tab').style.display = 'none';
  }

  userLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentUser(null);

    this.router.navigate(['/index']);
  }
}
