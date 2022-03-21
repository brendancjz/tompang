/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
import entity.Listing;
import entity.Message;
import entity.User;
import exception.CreateNewConversationException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
    public Long createNewConversation(Conversation convo, Long listingId, Long userId) {
        try {
            Listing listing = listingSessionBeanLocal.getListingByListingId(listingId);
            User user = userSessionBeanLocal.getUserByUserId(userId);
            if (!user.getConversations().contains(convo)) {
                user.getConversations().add(convo);
            }
            if (!listing.getConversations().contains(convo)) {
                listing.getConversations().add(convo);
            }

            em.persist(convo);
            em.flush();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

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
    public List<Conversation> retrieveAllBuyerConversations(Long userId) throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM Conversation c WHERE c.createdBy.userId = :id");
        query.setParameter("id", userId);

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
    public List<Conversation> retrieveAllSellerConversations(Long userId) throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM Conversation c WHERE c.listing.createdBy.userId = :id");
        query.setParameter("id", userId);

        List<Conversation> convos = query.getResultList();
        System.out.println("method called");
        System.out.println(convos);
        if (convos.isEmpty()) {
            throw new EmptyListException("List of convos is empty.\n");
        }

        for (Conversation convo : convos) {
            convo.getMessages().size();
        }

        return convos;
    }

    public Conversation getUserConversationWithListing(Long userId, Long listingId) throws EntityNotFoundException {
        System.out.println("conversationSessionBean.getUserConversationWithListing");

        Query query = em.createQuery("SELECT c FROM Conversation c WHERE c.createdBy.userId = :uId AND c.listing.listingId = :lId");

        query.setParameter("uId", userId);
        query.setParameter("lId", listingId);
        Conversation convo;
        try {
            convo = (Conversation) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new EntityNotFoundException("Conversation not found.\n");
        }

        convo.getMessages().size();

        return convo;
    }

    @Override
    public Conversation getConversationByConvoId(Long convoId) throws EntityNotFoundException {

        Conversation convo = em.find(Conversation.class, convoId);

        if (convo == null) {
            throw new EntityNotFoundException("Conversation " + convoId + " not found.\n");
        }

        convo.getMessages().size();
        Collections.sort(convo.getMessages());

        return convo;
    }

    @Override
    public void addMessage(Long convoId, Message message) throws EntityNotFoundException {
        Conversation convo = this.getConversationByConvoId(convoId);
        if (message.getFromBuyer()) {
            convo.setBuyerUnread(0);
            int sellerUnread = convo.getSellerUnread();
            convo.setSellerUnread(sellerUnread + 1);
        } else {
            convo.setSellerUnread(0);
            int buyerUnread = convo.getBuyerUnread();
            convo.setBuyerUnread(buyerUnread + 1);
        }
        convo.getMessages().add(message);
    }

}
