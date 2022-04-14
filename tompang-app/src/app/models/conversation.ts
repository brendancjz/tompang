import { Listing } from './listing';
import { Message } from './message';
import { User } from './user';

export class Conversation {
  convoId: number | undefined;
  isOpen: boolean | undefined;
  buyerUnread: number | undefined;
  sellerUnread: number | undefined;
  lastMessageDate: Date | undefined;

  // @ManyToOne
  createdBy: User | undefined;
  // @ManyToOne
  seller: User | undefined;
  // @ManyToOne
  listing: Listing | undefined;
  // @OneToMany
  messages: Message[] | undefined;

    constructor(convoId?: number) {
        this.convoId = convoId;
        this.isOpen = true;
        this.buyerUnread = 0;
        this.sellerUnread = 0;
        this.messages = [];
      }
}


