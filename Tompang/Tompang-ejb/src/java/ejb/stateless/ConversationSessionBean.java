/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
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
public class ConversationSessionBean implements ConversationSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewConversation(Conversation convo) {
        em.persist(convo);
        em.flush();

        return convo.getConvoId();
    }

    @Override
    public List<Conversation> retrieveAllConversations() throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM Conversation c");

        List<Conversation> convos = query.getResultList();

        if (convos.isEmpty()) {
            throw new EmptyListException("List of convos is empty.\n");
        }

        for (Conversation convo : convos) {
            convo.getMessages().size();
        }

        return convos; 
    }

    @Override
    public Conversation getConversationByConvoId(Long convoId) throws EntityNotFoundException {
        Conversation convo = em.find(Conversation.class, convoId);

        if (convo == null) {
            throw new EntityNotFoundException("Conversation " + convoId + " not found.\n");
        }

        convo.getMessages().size();

        return convo;
    }
    
}
