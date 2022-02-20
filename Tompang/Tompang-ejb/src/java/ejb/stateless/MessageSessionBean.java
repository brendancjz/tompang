/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Message;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brend
 */
@Stateless
public class MessageSessionBean implements MessageSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewMessage(Message msg) {
        em.persist(msg);
        em.flush();
        
        return msg.getMessageId();
    }
    
    @Override
    public List<Message> retrieveAllMessages() throws EmptyListException {
        Query query = em.createQuery("SELECT m FROM Message m");
        
        List<Message> messages = query.getResultList();
        
        if (messages.isEmpty()) throw new EmptyListException("List of messages is empty.\n");
        
        return messages;
    }
    
    @Override
    public Message getMessageByMessageId(Long messageId) throws EntityNotFoundException {
        Message msg = em.find(Message.class, messageId);
        
        if (msg == null) throw new EntityNotFoundException("Message " + messageId + " not found.\n");
        
        return msg;
    }
    
    @Override
    public void deleteMessage(Long messageId) throws EntityNotFoundException {
        Message msg = em.find(Message.class, messageId);
        em.remove(msg);
    }
}
