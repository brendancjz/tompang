import { Transaction } from './transaction';

export class CreateTransactionReq {
  username: string | undefined;
  password: string | undefined;
  listingId: number | undefined | null;
  transaction: Transaction | undefined;
  

  constructor(
    username?: string,
    password?: string,
    listingId?: number | null,
    transaction?: Transaction,
  ) {
    this.username = username;
    this.password = password;
    this.listingId = listingId;
    this.transaction = transaction;
    
  }
}
