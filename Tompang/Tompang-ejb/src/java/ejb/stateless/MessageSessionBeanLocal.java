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
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface MessageSessionBeanLocal {

    public Long createNewMessage(Message msg);

    public List<Message> retrieveAllMessages() throws EmptyListException;

    public Message getMessageByMessageId(Long messageId) throws EntityNotFoundException;

    public void deleteMessage(Long messageId) throws EntityNotFoundException;
    
}
