import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Conversation } from '../models/conversation';
import { Listing } from '../models/listing';
import { ConversationService } from '../services/conversation.service';
import { ListingService } from '../services/listing.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-listing-details',
  templateUrl: './view-listing-details.page.html',
  styleUrls: ['./view-listing-details.page.scss'],
})
export class ViewListingDetailsPage implements OnInit {

  listingId: string | null;
  listingToView: Listing | null;
  retrieveListingError: boolean;

  hasLoaded: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private listingService: ListingService,
    private conversationService: ConversationService) { }

  ngOnInit() {
    this.listingId = this.activatedRoute.snapshot.paramMap.get('listingId');

    if(this.listingId != null)
    {

      this.listingService.getAllAvailableListings().subscribe({
        next:(response)=> {
          this.listingToView = response[9];
          this.hasLoaded = true;
        },
        error:(error)=>{
          this.retrieveListingError = true;
					console.log('********** View Listing Details Page.ts: ' + error);
        }
      });

      // eslint-disable-next-line radix
      // this.listingService.getListingByListingId(parseInt(this.listingId)).subscribe({
      //   next:(response)=>{
      //     this.listingToView = response;
      //   },
      //   error:(error)=>{
      //     this.retrieveListingError = true;
			// 		console.log('********** View Listing Details Page.ts: ' + error);
      //   }
      // });
    }
  }

  ionViewDidEnter() {

  }

  getPhotoUrl(photo: string) {
    const baseUrl = '../../assets/images';
    return baseUrl + photo;
  }

  formatListingCreatedBy(): string {
    // return this.listingToView.createdBy.username;
    return '@stackoverflow';
  }
  formatListingCategory(): string {
    const category = this.listingToView.category;
    return category.charAt(0) + category.substring(1).toLowerCase();
  }
  viewUserProfile(): void {
    console.log('Viewing user profile');
  }

  viewListingConversation(): void {
    console.log('View Listing Conversation..');

    const currentUser = this.sessionService.getCurrentUser();

    let convo: Conversation;

    try {
      //Have a current convo
      // eslint-disable-next-line radix
      convo = this.conversationService.getUserConversationWithListing(currentUser.userId, parseInt(this.listingId));

    } catch (ex) {
      //Need to create a new convo
      // System.out.println(ex.getMessage());
      // convo = new Conversation(user, listing);
      // conversationSessionBean.createNewConversation(convo, listing.getListingId(), user.getUserId());
    }

    this.router.navigate(['/view-conversation/' + convo.convoId]);
  }

  makeTransaction(): void {
    console.log('Make transaction..');
  }
}
