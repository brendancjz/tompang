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

  //TODO
  // @OneToMany
  // private List<CreditCard> creditCards;
  // @OneToMany(mappedBy = "createdBy")
  // private List<Conversation> conversations;
  // @OneToMany(mappedBy = "createdBy")
  // private List<Listing> createdListings;
  // @OneToMany(mappedBy = "buyer")
  // private List<Transaction> buyerTransactions;
  // @OneToMany(mappedBy = "seller")
  // private List<Transaction> sellerTransactions;
  // @ManyToMany
  // private List<User> followers;
  // @ManyToMany(mappedBy = "followers")
  // private List<User> following;
  // @OneToMany
  // private List<Listing> likedListings;

    constructor(userId?: number, firstName?: string, lastName?: string, username?: string,
      password?: string, email?: string, dateOfBirth?: Date, profilePic?: string,
      contactNumber?: number)
	{
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
	}
}
