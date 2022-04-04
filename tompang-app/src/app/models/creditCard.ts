export class CreditCard {
  ccId: number | undefined;
  ccBrand: string | undefined;
  ccName: string | undefined;
  ccNumber: number | undefined;
  ccCIV: number | undefined;
  expiryDate: Date | undefined;

    constructor(ccId?: number, ccBrand?: string, ccName?: string, ccNumber?: number, ccCIV?: number,
      expiryDate?: Date) {
        this.ccId = ccId;
        this.ccBrand = ccBrand;
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.ccCIV = ccCIV;
        this.expiryDate = expiryDate;
      }
}
