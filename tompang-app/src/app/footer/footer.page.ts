import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.page.html',
  styleUrls: ['./footer.page.scss'],
})
export class FooterPage implements OnInit {

  constructor(private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
  }

  back()
	{
		this.location.back();
    // this.resetPage();
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

  //Need to repeat this method in the Shop page as well.
  createListingPage() {
    console.log('Going to create listing page');
  }

  //Need to repeat this method in the Shop page as well.
  profilePage() {
    this.router.navigate(['/profile']);
  }

}
