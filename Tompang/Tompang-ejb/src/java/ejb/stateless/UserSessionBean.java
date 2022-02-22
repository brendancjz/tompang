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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brend
 */
@Stateless
public class UserSessionBean implements UserSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewUser(User user) {
        em.persist(user);
        em.flush();

        return user.getUserId();
    }

    @Override
    public List<User> retrieveAllUsers() throws EmptyListException {
        Query query = em.createQuery("SELECT u FROM User u");

        List<User> users = query.getResultList();

        if (users.isEmpty()) {
            throw new EmptyListException("List of users is empty.\n");
        }

        for (User user : users) {
            user.getBuyerTransactions().size();
            user.getSellerTransactions().size();
            user.getConversations().size();
            user.getListings().size();
            user.getCreditCards().size();
            user.getFollowing().size();
        }

        return users; 
    }

    @Override
    public User getUserByUserId(Long userId) throws EntityNotFoundException {
        User user = em.find(User.class, userId);

        if (user == null) {
            throw new EntityNotFoundException("User " + userId + " not found.\n");
        }

        user.getBuyerTransactions().size();
        user.getSellerTransactions().size();
        user.getConversations().size();
        user.getListings().size();
        user.getCreditCards().size();
        user.getFollowing().size();

        return user;
    }

    @Override
    public void updateUserDetails(Long userId, String firstName, String lastName, String email, String username, Date dob, Long contactNum) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setDateOfBirth(dob);
        user.setContactNumber(contactNum);
    }

    @Override
    public void updateUserPassword(Long userId, String password) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        user.setPassword(password);
    }

    @Override
    public void associateListingWithUser(Listing listing, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getListings().contains(listing)) {
            user.getListings().add(listing);
        }
    }

    @Override
    public void associateFollowingUserWithUser(User userToFollow, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getFollowing().contains(userToFollow)) {
            user.getFollowing().add(userToFollow);
        }
    }

    @Override
    public void associateCrediCardWithUser(CreditCard cc, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getCreditCards().contains(cc)) {
            user.getCreditCards().add(cc);
        }
    }

    @Override
    public void associateConversationWithUser(Conversation convo, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getConversations().contains(convo)) {
            user.getConversations().add(convo);
        }
    }

    @Override
    public void associateBuyerTransactionWithUser(Transaction transaction, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getBuyerTransactions().contains(transaction)) {
            user.getBuyerTransactions().add(transaction);
        }
    }

    @Override
    public void associateSellerTransactionWithUser(Transaction transaction, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getSellerTransactions().contains(transaction)) {
            user.getSellerTransactions().add(transaction);
        }
    }

    @Override
    public void deletUser(Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getBuyerTransactions().isEmpty()
                || !user.getSellerTransactions().isEmpty()
                || !user.getConversations().isEmpty()
                || !user.getListings().isEmpty()
                || !user.getCreditCards().isEmpty()
                || !user.getFollowing().isEmpty()) {
            user.setIsDisabled(true);
        } else {
            em.remove(user);
        }

    }

}
