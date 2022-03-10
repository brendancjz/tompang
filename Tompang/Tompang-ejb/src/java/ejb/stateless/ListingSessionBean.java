/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
import entity.Listing;
import entity.Transaction;
import entity.User;
import exception.CreateNewListingException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author GuoJun
 */
@Stateless
public class ListingSessionBean implements ListingSessionBeanLocal {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewListing(Listing listing, Long userId) throws CreateNewListingException {
        try {
            User user = userSessionBeanLocal.getUserByUserId(userId);
            if (!user.getCreatedListings().contains(listing)) {
                user.getCreatedListings().add(listing);
            }
        } catch (EntityNotFoundException ex) {
            throw new CreateNewListingException();
        }
        em.persist(listing);
        em.flush();

        return listing.getListingId();
    }

    @Override
    public List<Listing> retrieveAllListings() throws EmptyListException {
        Query query = em.createQuery("SELECT l FROM Listing l");

        List<Listing> listings = query.getResultList();

        if (listings.isEmpty()) {
            throw new EmptyListException("List of listings is empty.\n");
        }

        for (Listing listing : listings) {
            listing.getConversations().size();
            listing.getPhotos().size();
        }

        return listings;
    }
    
    @Override
    public List<Listing> retrieveUserListings(String filteredUsername) throws EmptyListException {
        
        if (filteredUsername == null) throw new EmptyListException("List of listings is empty.\n");
        
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.createdBy.username = :name");
        query.setParameter("name", filteredUsername);
        
        List<Listing> listings = query.getResultList();

        if (listings.isEmpty()) {
            throw new EmptyListException("List of listings is empty.\n");
        }

        for (Listing listing : listings) {
            listing.getConversations().size();
            listing.getPhotos().size();
        }

        return listings;
    }

    @Override
    public Listing getListingByListingId(long listingId) throws EntityNotFoundException {
        Listing listing = em.find(Listing.class, listingId);

        if (listing == null) {
            throw new EntityNotFoundException("Listing " + listingId + " not found.\n");
        }

        listing.getConversations().size();
        listing.getPhotos().size();

        return listing;
    }

    @Override
    public void updateListingDetails(Long listingId, String country, String city, String title, String description, double price, Date expectedArrivalDate, int quantity, boolean isOpen, List<String> photos) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);

        listing.setCountry(country);
        listing.setCity(city);
        listing.setTitle(country);
        listing.setDescription(description);
        listing.setPrice(price);
        listing.setExpectedArrivalDate(expectedArrivalDate);
        listing.setQuantity(quantity);
        listing.setIsOpen(isOpen);
        listing.setPhotos(photos);
    }

    @Override
    public void deleteListing(long listingId) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);
        User user = listing.getCreatedBy();
        if (!listing.getTransactions().isEmpty()) {
            listing.setIsDisabled(true);
        } else {
            user.getCreatedListings().remove(listing);
            em.remove(listing);
        }
    }

//    @Override
//    public void associateUserWithListing(User user, long listingId) throws EntityNotFoundException {
//        Listing listing = this.getListingByListingId(listingId);
//
//        listing.setCreatedBy(user);
//    }

//    @Override
//    public void associateConversationWithListing(Conversation conversation, long listingId) throws EntityNotFoundException {
//        Listing listing = this.getListingByListingId(listingId);
//
//        if (!listing.getConversations().contains(conversation)) {
//            listing.getConversations().add(conversation);
//        }
//    }
    @Override
    public void associateTransactionWithListing(Transaction transaction, long listingId) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);

        if (!listing.getTransactions().contains(transaction)) {
            listing.getTransactions().add(transaction);
        }
    }

    

}
