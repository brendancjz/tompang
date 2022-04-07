import { Message } from './message';

export class NewMessageReq {

    newMessage: Message | undefined;
    convoId: number | undefined;

    constructor(newMessage?: Message, convoId?: number) {
        this.newMessage = newMessage;
        this.convoId = convoId;
    }
}
