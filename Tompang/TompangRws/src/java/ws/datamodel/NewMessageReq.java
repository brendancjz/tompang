/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Message;

/**
 *
 * @author ignit
 */
public class NewMessageReq {
    
    private Message newMessage;
    private Long convoId;
    
    public NewMessageReq() {

    }

    public NewMessageReq(Message newMessage, Long convoId) {
        this.newMessage = newMessage;
        this.convoId = convoId;
    }

    /**
     * @return the newMessage
     */
    public Message getNewMessage() {
        return newMessage;
    }

    /**
     * @param newMessage the newMessage to set
     */
    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    /**
     * @return the convoId
     */
    public Long getConvoId() {
        return convoId;
    }

    /**
     * @param convoId the convoId to set
     */
    public void setConvoId(Long convoId) {
        this.convoId = convoId;
    }
}
