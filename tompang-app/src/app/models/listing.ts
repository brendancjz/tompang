import { Conversation } from './conversation';
import { Transaction } from './transaction';
import { User } from './user';

export class Listing {
  listingId: number | undefined;
  country: string | undefined;
  city: string | undefined;
  title: string | undefined;
  description: string | undefined;
  category: string | undefined;
  price: number | undefined;
  createdOn: Date | undefined;
  expectedArrivalDate: Date | undefined;
  numOfLikes: number | undefined;
  quantity: number | undefined;
  isOpen: boolean | undefined;
  isDisabled: boolean | undefined;

  // @Column(nullable = false)
  // @NotNull
  photos: string[] | undefined;
  // private List<String> photos;

  // @ManyToOne
  createdBy: User | undefined;
  // @OneToMany(mappedBy = "listing")
  conversations: Conversation[] | undefined;
  // private List<Conversation> conversations;
  transactions: Transaction[] | undefined;

    constructor(listingId?: number, country?: string, city?: string, title?: string, description?: string,
      category?: string, price?: number, expectedArrivalDate?: Date, quantity?: number) {
        this.listingId = listingId;
        this.country = country;
        this.city = city;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.createdOn = new Date();
        this.expectedArrivalDate = expectedArrivalDate;
        this.numOfLikes = 0;
        this.quantity = quantity;
        this.isOpen = true;
        this.isDisabled = false;

        this.photos = [];
    }
}
