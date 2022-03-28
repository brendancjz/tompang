export class Conversation {
  convoId: number | undefined;
  isOpen: boolean | undefined;
  buyerUnread: number | undefined;
  sellerUnread: number | undefined;
    // @ManyToOne
    // private User createdBy;
    // @ManyToOne
    // private Listing listing;
    // @OneToMany
    // private List<Message> messages;

    constructor(convoId?: number, isOpen?: boolean, buyerUnread?: number,
      sellerUnread?: number) {
        this.convoId = convoId;
        this.isOpen = isOpen;
        this.buyerUnread = buyerUnread;
        this.sellerUnread = sellerUnread;
      }
}


