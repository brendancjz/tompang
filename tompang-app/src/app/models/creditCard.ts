export class CreditCard {
  ccId: number | undefined;
  ccName: string | undefined;
  ccNumber: number | undefined;
  ccCIV: number | undefined;
  expiryDate: Date | undefined;

    constructor(ccId?: number, ccName?: string, ccNumber?: number, ccCIV?: number,
      expiryDate?: Date) {
        this.ccId = ccId;
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.ccCIV = ccCIV;
        this.expiryDate = expiryDate;
      }
}
