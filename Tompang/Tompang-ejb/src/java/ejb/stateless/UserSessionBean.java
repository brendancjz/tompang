/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Listing;
import entity.User;
import exception.CreateNewUserException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import java.util.Date;
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
public class UserSessionBean implements UserSessionBeanLocal {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewUser(User user) throws CreateNewUserException {

        String username = user.getUsername();
        String email = user.getEmail();
        
        Query query1 = em.createQuery("SELECT u FROM User u WHERE u.email = ?1");
        Query query2 = em.createQuery("SELECT u FROM User u WHERE u.username = ?1");
        
        query1.setParameter(1, email);
        query2.setParameter(1, username);
        
        List<User> list1 = query1.getResultList();
        List<User> list2 = query2.getResultList();
        
        if (list1.isEmpty() && list2.isEmpty()){
            em.persist(user);
            em.flush();
            return user.getUserId();
        } else {
            throw new CreateNewUserException("Username or email exists!");
        }
           
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
            user.getCreatedListings().size();
            user.getCreditCards().size();
            user.getFollowing().size();
            user.getFollowers().size();
            user.getLikedListings().size();
        }

        return users;
    }

    @Override
    public List<User> retrieveAllNotDisabledUsers() throws EmptyListException {

        List<User> users = this.retrieveAllUsers();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getIsDisabled()) {
                users.remove(user);
            }
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
        user.getCreatedListings().size();
        user.getCreditCards().size();
        user.getFollowing().size();
        user.getFollowers().size();
        user.getLikedListings().size();

        return user;
    }

    @Override
    public void updateUserDetails(Long userId, String firstName, String lastName, String email, String username, Date dob, String profilePic, Long contactNum) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setDateOfBirth(dob);
        user.setProfilePic(profilePic);
        user.setContactNumber(contactNum);
    }
    
    @Override
    public void updateUserDetails(User userToUpdate) throws EntityNotFoundException {
        User user = this.getUserByUserId(userToUpdate.getUserId());
        user.setFirstName(userToUpdate.getFirstName());
        user.setLastName(userToUpdate.getLastName());
        user.setEmail(userToUpdate.getEmail());
        user.setUsername(userToUpdate.getUsername());
        user.setDateOfBirth(userToUpdate.getDateOfBirth());
        user.setProfilePic(userToUpdate.getProfilePic());
        user.setContactNumber(userToUpdate.getContactNumber());
        user.setWalletAmount(userToUpdate.getWalletAmount());
        
    }

    @Override
    public void updateUserPassword(Long userId, String password) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        user.setPassword(password);
    }

//    @Override
//    public void associateCreatedListingWithUser(Listing listing, Long userId) throws EntityNotFoundException {
//        User user = this.getUserByUserId(userId);
//
//        if (!user.getCreatedListings().contains(listing)) {
//            user.getCreatedListings().add(listing);
//        }
//    }
    @Override
    public void associateLikedListingWithUser(Listing listing, Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getLikedListings().contains(listing)) {
            user.getLikedListings().add(listing);
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
    public void deleteUser(Long userId) throws EntityNotFoundException {
        User user = this.getUserByUserId(userId);

        if (!user.getBuyerTransactions().isEmpty()
                || !user.getSellerTransactions().isEmpty()
                || !user.getConversations().isEmpty()
                || !user.getCreatedListings().isEmpty()
                || !user.getCreditCards().isEmpty()
                || !user.getFollowing().isEmpty()
                || !user.getLikedListings().isEmpty()) {
            user.setIsDisabled(true);
        } else {
            em.remove(user);
            System.out.println("Successfully deleted User from sesison bean.");
        }

    }

    @Override
    public User retrieveUserByUsername(String username) throws EntityNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public User userLogin(String username, String password) throws InvalidLoginCredentialsException {
        try {
            User user = retrieveUserByUsername(username);
            // String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + user.getSalt()));

            if (user.getPassword().equals(password)) {
                user.getBuyerTransactions().size();
                user.getSellerTransactions().size();
                user.getConversations().size();
                user.getCreatedListings().size();
                user.getCreditCards().size();
                user.getFollowing().size();
                user.getLikedListings().size();
                return user;
            } else {
                throw new InvalidLoginCredentialsException("Username does not exist or invalid password!");
            }
        } catch (EntityNotFoundException ex) {
            throw new InvalidLoginCredentialsException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void removeListingFromUserLikedListings(Long listingId) throws EmptyListException, EntityNotFoundException {

        Query query = em.createQuery("SELECT u FROM User u, IN (u.likedListings) l WHERE l.listingId = :id");
        query.setParameter("id", listingId);
        List<User> users = query.getResultList();

        Listing listing = listingSessionBean.getListingByListingId(listingId);
        
        if (users.isEmpty()) {
            throw new EmptyListException("List of users is empty.\n");
        }

        for (User user : users) {
            user.getLikedListings().remove(listing);
        }

    }

    @Override
    public void follow(Long followingUserId, Long followedUserId) throws EntityNotFoundException {
        User followingUser = this.getUserByUserId(followingUserId);
        User followedUser = this.getUserByUserId(followedUserId);
        
        System.out.print("Following user: " + followingUser.getUsername() + " Followed user: " + followedUser.getUsername());

        followingUser.getFollowing().add(followedUser);
        followedUser.getFollowers().add(followingUser);
    }

    @Override
    public void unfollow(Long unfollowingUserId, Long unfollowedUserId) throws EntityNotFoundException {
        User unfollowingUser = this.getUserByUserId(unfollowingUserId);
        User unfollowedUser = this.getUserByUserId(unfollowedUserId);
        
        System.out.print("Unfollowing user: " + unfollowingUser.getUsername() + " Unfollowed user: " + unfollowedUser.getUsername());
        unfollowingUser.getFollowing().remove(unfollowedUser);
        unfollowedUser.getFollowers().remove(unfollowingUser);
    }
}
