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

    public Long createNewConversation(Conversation convo, Long listingId, Long userId, Message firstMessage) throws CreateNewConversationException;

    public List<Conversation> retrieveAllConversations() throws EmptyListException;

    public Conversation getConversationByConvoId(Long convoId) throws EntityNotFoundException;

    public void addMessage(Long convoId, Message message) throws EntityNotFoundException;
    
}
