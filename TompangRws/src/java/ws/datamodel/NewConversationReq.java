/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Conversation;

/**
 *
 * @author ignit
 */
public class NewConversationReq {
    
    private Conversation newConversation;
    private Long listingId;
    private Long userId;
    
    public NewConversationReq() {

    }

    public NewConversationReq(Conversation newConversation, Long listingId, Long userId) {
        this.newConversation = newConversation;
        this.listingId = listingId;
        this.userId = userId;
    }

    /**
     * @return the newConversation
     */
    public Conversation getNewConversation() {
        return newConversation;
    }

    /**
     * @param newConversation the newConversation to set
     */
    public void setNewConversation(Conversation newConversation) {
        this.newConversation = newConversation;
    }

    /**
     * @return the listingId
     */
    public Long getListingId() {
        return listingId;
    }

    /**
     * @param listingId the listingId to set
     */
    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
