import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
})
export class SettingsPage implements OnInit {

  toggleSettingTabs: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) { }

  ngOnInit() {
    this.toggleSettingTabs = true;
  }

  hideSettingTabs() {
    console.log('Hiding Setting Tabs...');
    this.toggleSettingTabs = false;
  }

  userLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentUser(null);

    this.router.navigate(['/index']);
  }

	ionViewWillEnter()
	{
		console.log('********** SettingsPage.ionViewWillEnter() ');
	}

	ionViewDidEnter()
	{
		console.log('********** SettingsPage.ionViewDidEnter() ');
	}

	ionViewWillLeave()
	{
		console.log('********** SettingsPage.ionViewWillLeave() ');
	}

	ionViewDidLeave()
	{
		console.log('********** SettingsPage.ionViewDidLeave() ');
	}
}
