/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
import entity.Message;
import exception.CreateNewConversationException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface ConversationSessionBeanLocal {

    public List<Conversation> retrieveAllConversations() throws EmptyListException;

    public Conversation getConversationByConvoId(Long convoId) throws EntityNotFoundException;

    public void addMessage(Long convoId, Message message) throws EntityNotFoundException;

    public Conversation getUserConversationWithListing(Long userId, Long listingId) throws EntityNotFoundException;

    public Long createNewConversation(Conversation convo, Long listingId, Long userId);

    public List<Conversation> retrieveAllBuyerConversations(Long userId) throws EmptyListException;

    public List<Conversation> retrieveAllSellerConversations(Long userId) throws EmptyListException;
    
}
