export class Message {

  messageId: number | undefined;
  createdOn: Date | undefined;
  body: string | undefined;
  fromBuyer: boolean | undefined;
  sentBy: number | undefined;
  readByBuyer: boolean | undefined;
  readBySeller: boolean | undefined;
  offerMessage: boolean | undefined;
  containsImage: boolean | undefined;
  imageUrl: string | undefined;

    constructor(messageId?: number, body?: string, fromBuyer?: boolean, sentBy?: number,
      readByBuyer?: boolean, readBySeller?: boolean, offerMessage?: boolean) {
        this.messageId = messageId;
        this.createdOn = new Date();
        this.body = body;
        this.fromBuyer = fromBuyer;
        this.sentBy = sentBy;
        this.readByBuyer = readByBuyer;
        this.readBySeller = readBySeller;
        this.offerMessage = offerMessage;
      }
}
