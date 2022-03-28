export class Dispute {
  disputeId: number | undefined;
  description: string | undefined;
  isResolved: boolean | undefined;


    // @OneToOne(mappedBy = "dispute")
    // private Transaction transaction;

    constructor(disputeId?: number, description?: string) {
      this.disputeId = disputeId;
      this.description = description;
      this.isResolved = false;
    }
}
