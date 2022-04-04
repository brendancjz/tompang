import { Transaction } from './transaction';

export class CreateTransactionReq {
  username: string | undefined;
  password: string | undefined;
  trasaction: Transaction | undefined;
  buyerId: number | undefined | null;

  constructor(
    username?: string,
    password?: string,
    transaction?: Transaction,
    buyerId?: number | null
  ) {
    this.username = username;
    this.password = password;
    this.trasaction = transaction;
    this.buyerId = buyerId;
  }
}
