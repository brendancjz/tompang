import { Conversation } from './conversation';
import { CreditCard } from './creditCard';
import { Listing } from './listing';
import { Transaction } from './transaction';

export class User {
  userId: number | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  username: string | undefined;
  password: string | undefined;
  email: string | undefined;
  dateOfBirth: Date | undefined;
  profilePic: string | undefined;
  contactNumber: number | undefined;
  joinedOn: Date | undefined;
  isAdmin: boolean | undefined;
  isDisabled: boolean | undefined;
  walletAmount: number | undefined;

  // @OneToMany
  creditCards: CreditCard[] | undefined;
  // @OneToMany(mappedBy = "createdBy")
  conversations: Conversation[] | undefined;
  // @OneToMany(mappedBy = "createdBy")
  createdListings: Listing[] | undefined;
  // @OneToMany(mappedBy = "buyer")
  buyerTransactions: Transaction[] | undefined;
  // @OneToMany(mappedBy = "seller")
  sellerTransactions: Transaction[] | undefined;
  // @ManyToMany
  followers: User[] | undefined;
  // @ManyToMany(mappedBy = "followers")
  following: User[] | undefined;
  // @OneToMany
  likedListings: Listing[] | undefined;

  constructor(
    userId?: number,
    firstName?: string,
    lastName?: string,
    username?: string,
    password?: string,
    email?: string,
    dateOfBirth?: Date,
    profilePic?: string,
    contactNumber?: number
  ) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.profilePic = profilePic;
    this.contactNumber = contactNumber;
    this.joinedOn = new Date();
    this.isAdmin = false;
    this.isDisabled = false;

    this.creditCards = [];
  }
}
