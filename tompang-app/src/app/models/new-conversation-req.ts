import { Conversation } from './conversation';

export class NewConversationReq {

    newConversation: Conversation | undefined;
    listingId: number | undefined;
    userId: number | undefined;

    constructor(newConversation?: Conversation, listingId?: number, userId?: number) {
        this.newConversation = newConversation;
        this.listingId = listingId;
        this.userId = userId;
    }
}
