/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
import entity.Listing;
import entity.User;
import exception.CreateNewConversationException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @EJB
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewConversation(Conversation convo, Long listingId, Long userId) throws CreateNewConversationException {
        try {
            Listing listing = listingSessionBeanLocal.getListingByListingId(listingId);
            User user = userSessionBeanLocal.getUserByUserId(userId);
            if (!user.getConversations().contains(convo)) {
                user.getConversations().add(convo);
            }
            if (!listing.getConversations().contains(convo)) {
                listing.getConversations().add(convo);
            }
        } catch (EntityNotFoundException ex) {
            throw new CreateNewConversationException();
        }
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
