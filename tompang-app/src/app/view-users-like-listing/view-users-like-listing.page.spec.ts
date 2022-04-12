import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewUsersLikeListingPage } from './view-users-like-listing.page';

describe('ViewUsersLikeListingPage', () => {
  let component: ViewUsersLikeListingPage;
  let fixture: ComponentFixture<ViewUsersLikeListingPage>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewUsersLikeListingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewUsersLikeListingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
