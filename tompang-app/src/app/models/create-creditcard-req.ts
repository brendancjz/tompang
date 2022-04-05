import { CreditCard } from './creditCard';

export class CreateCreditCardReq {
  username: string | undefined;
  password: string | undefined;
  creditCard: CreditCard | undefined;

  constructor(username?: string, password?: string, creditCard?: CreditCard) {
    this.username = username;
    this.password = password;
    this.creditCard = creditCard;
  }
}
