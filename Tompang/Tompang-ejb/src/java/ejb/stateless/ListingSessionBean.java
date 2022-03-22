/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

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
    public List<Listing> retrieveAllAvailableListings() throws EmptyListException {
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.isDisabled = false AND l.isOpen = true");

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

        if (filteredUsername == null) {
            throw new EmptyListException("List of listings is empty.\n");
        }

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
    public void incrementListingLikes(Long listingId) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);

        listing.setNumOfLikes(listing.getNumOfLikes() + 1);
    }

    @Override
    public void decrementListingLikes(Long listingId) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);

        listing.setNumOfLikes(listing.getNumOfLikes() - 1);
    }

    @Override
    public Listing getListingByListingId(Long listingId) throws EntityNotFoundException {
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
    public void updateListingDetails(Listing listingToUpdate) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingToUpdate.getListingId());
        listing.setCategory(listingToUpdate.getCategory());
        listing.setCity(listingToUpdate.getCity());
        listing.setCountry(listingToUpdate.getCountry());
        listing.setCreatedOn(listingToUpdate.getCreatedOn());
        listing.setDescription(listingToUpdate.getDescription());
        listing.setExpectedArrivalDate(listingToUpdate.getExpectedArrivalDate());
        listing.setIsDisabled(listingToUpdate.getIsDisabled());
        listing.setIsOpen(listingToUpdate.getIsOpen());
        listing.setNumOfLikes(listingToUpdate.getNumOfLikes());
        listing.setPhotos(listingToUpdate.getPhotos());
        listing.setPrice(listing.getPrice());
        listing.setQuantity(listingToUpdate.getQuantity());
    }

    @Override
    public void deleteListing(Long listingId) throws EntityNotFoundException {
        try {
            Listing listing = this.getListingByListingId(listingId);
            User user = listing.getCreatedBy();
            if (!listing.getTransactions().isEmpty() || !listing.getConversations().isEmpty()) {
                listing.setIsDisabled(true);
            } else {

                userSessionBeanLocal.removeListingFromUserLikedListings(listing.getListingId());
                user.getCreatedListings().remove(listing);
                em.remove(listing);

            }

        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
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
    public void associateTransactionWithListing(Transaction transaction, Long listingId) throws EntityNotFoundException {
        Listing listing = this.getListingByListingId(listingId);

        if (!listing.getTransactions().contains(transaction)) {
            listing.getTransactions().add(transaction);
        }
    }

}
