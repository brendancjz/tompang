/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Listing;
import entity.Transaction;
import exception.CreateNewListingException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GuoJun
 */
@Local
public interface ListingSessionBeanLocal {

    Long createNewListing(Listing listing, Long userId) throws CreateNewListingException;

    List<Listing> retrieveAllListings() throws EmptyListException;

    Listing getListingByListingId(long listingId) throws EntityNotFoundException;

    void updateListingDetails(Long listingId, String country, String city, String title, String description, double price, Date expectedArrivalDate, int quantity, boolean isOpen, List<String> photos) throws EntityNotFoundException;

    void deleteListing(long listingId) throws EntityNotFoundException;

//    void associateUserWithListing(User user, long listingId) throws EntityNotFoundException;

//    void associateConversationWithListing(Conversation conversation, long listingId) throws EntityNotFoundException;
            
    void associateTransactionWithListing(Transaction transaction, long listingId) throws EntityNotFoundException;

    public List<Listing> retrieveUserListings(String username)  throws EmptyListException;

    public List<Listing> retrieveAllAvailableListings() throws EmptyListException;
}
