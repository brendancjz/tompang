import { Listing } from './listing';

export class CreateListingReq {
  username: string | undefined;
  password: string | undefined;
  listing: Listing | undefined;

  constructor(username?: string, password?: string, listing?: Listing) {
    this.username = username;
    this.password = password;
    this.listing = listing;
  }
}
