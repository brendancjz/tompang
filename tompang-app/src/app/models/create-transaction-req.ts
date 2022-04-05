import { Transaction } from './transaction';

export class CreateTransactionReq {
  username: string | undefined;
  password: string | undefined;
  transaction: Transaction | undefined;
  buyerId: number | undefined | null;

  constructor(
    username?: string,
    password?: string,
    transaction?: Transaction,
    buyerId?: number | null
  ) {
    this.username = username;
    this.password = password;
    this.transaction = transaction;
    this.buyerId = buyerId;
  }
}
