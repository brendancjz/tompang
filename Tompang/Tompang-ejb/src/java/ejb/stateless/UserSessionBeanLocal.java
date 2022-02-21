/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Conversation;
import entity.CreditCard;
import entity.Listing;
import entity.Transaction;
import entity.User;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface UserSessionBeanLocal {

    public Long createNewUser(User user);

    public List<User> retrieveAllUsers() throws EmptyListException;

    public User getUserByUserId(Long userId) throws EntityNotFoundException;

    public void updateUserDetails(Long userId, String firstName, String lastName, String email, String username, Date dob, Long contactNum) throws EntityNotFoundException;

    public void updateUserPassword(Long userId, String password) throws EntityNotFoundException;

    public void associateListingWithUser(Listing listing, Long userId) throws EntityNotFoundException;

    public void associateFollowingUserWithUser(User userToFollow, Long userId) throws EntityNotFoundException;

    public void associateCrediCardWithUser(CreditCard cc, Long userId) throws EntityNotFoundException;

    public void associateConversationWithUser(Conversation convo, Long userId) throws EntityNotFoundException;

    public void associateBuyerTransactionWithUser(Transaction transaction, Long userId) throws EntityNotFoundException;

    public void associateSellerTransactionWithUser(Transaction transaction, Long userId) throws EntityNotFoundException;
    
}
