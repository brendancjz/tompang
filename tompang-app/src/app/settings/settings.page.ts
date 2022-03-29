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

  ngOnInit() {

  }

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

  showSettingTabs() {
    //This is for Settings Page
    const transTab = document.getElementById('transparent-tab');
    const editProfTab = document.getElementById('edit-profile-tab');
    const changeProfPicTab = document.getElementById('change-profile-pic-tab');
    const changePassTab = document.getElementById('change-password-tab');
    const creditCardsTab = document.getElementById('manage-credit-cards-tab');
    const logoutTab = document.getElementById('logout-tab');
    const tabs = [transTab, editProfTab, changeProfPicTab, changePassTab, creditCardsTab, logoutTab];
    for (const elem of tabs) {
      if (elem !== null) {
        elem.style.display = 'block';
      }
    }
  }

	ionViewDidEnter()
	{
    console.log("view did enter");
		document.getElementById('back-button').addEventListener('click', this.showSettingTabs);

	}

	ionViewDidLeave()
	{
    console.log("view did leave");
		document.getElementById('back-button').removeEventListener('click', this.showSettingTabs);
	}
}
