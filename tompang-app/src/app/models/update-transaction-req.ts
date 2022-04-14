import { Transaction } from './transaction';

export class UpdateTransactionReq {
  username: string | undefined;
  password: string | undefined;
  transactionId: number| undefined;

  constructor(
    username?: string,
    password?: string,
    transactionId?: number | null
  ) {
    this.username = username;
    this.password = password;
    this.transactionId = transactionId;
  }
}
