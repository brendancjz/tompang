import { Dispute } from './dispute';

export class CreateDisputeReq {
  username: string | undefined;
  password: string | undefined;
  dispute: Dispute | undefined;
  transactionId: number | undefined | null;

  constructor(
    username?: string,
    password?: string,
    dispute?: Dispute,
    transactionId?: number | null
  ) {
    this.username = username;
    this.password = password;
    this.dispute = dispute;
    this.transactionId = transactionId;
  }
}
