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
    // private List<String> photos;

    // @ManyToOne
    // private User createdBy;
    // @OneToMany(mappedBy = "listing")
    // private List<Conversation> conversations;
    // @OneToMany(mappedBy = "listing")
    // private List<Transaction> transactions;

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
    }
}
