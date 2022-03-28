export class Transaction {

  transactionId: number | undefined;
  amount: number | undefined;
  createdOn: Date | undefined;
  isCompleted: boolean | undefined;
  hasDispute: boolean | undefined;
  isAccepted: boolean | undefined;


    // @ManyToOne(optional = false)
    // @JoinColumn(nullable = false)
    // private User buyer;
    // @ManyToOne(optional = false)
    // @JoinColumn(nullable = false)
    // private User seller;
    // @ManyToOne(optional = false)
    // @JoinColumn(nullable = false)
    // private Listing listing;
    // @OneToOne
    // private Dispute dispute;
    // @ManyToOne(optional = false)
    // @JoinColumn(nullable = false)
    // private CreditCard creditCard;
    constructor(transactionId?: number, amount?: number) {
      this.transactionId = transactionId;
      this.amount = amount;
      this.createdOn = new Date();
      this.isCompleted = false;
      this.hasDispute = false;
      this.isAccepted = false;
    }

}
