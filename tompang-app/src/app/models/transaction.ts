import { CreditCard } from './creditCard';
import { Dispute } from './dispute';
import { Listing } from './listing';
import { User } from './user';

export class Transaction {

  transactionId: number | undefined;
  amount: number | undefined;
  createdOn: Date | undefined;
  isCompleted: boolean | undefined;
  hasDispute: boolean | undefined;
  isAccepted: boolean | undefined;
  isRejected: boolean | undefined;
  quantity: number | undefined;
  month: number | undefined;

  // @ManyToOne(optional = false)
  buyer: User | undefined;
  // @ManyToOne(optional = false)
  seller: User | undefined;
  // @ManyToOne(optional = false)
  listing: Listing | undefined;
  // @OneToOne
  dispute: Dispute | undefined;
  // @ManyToOne(optional = false)
  creditCard: CreditCard | undefined;

    constructor(transactionId?: number, amount?: number, quantity?: number, month?: number) {
      this.transactionId = transactionId;
      this.amount = amount;
      this.createdOn = new Date();
      this.isCompleted = false;
      this.hasDispute = false;
      this.isAccepted = false;
      this.isRejected = false;
      this.quantity = quantity;
      this.month = amount;
    }

}
