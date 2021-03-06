package ejb.stateless;

import entity.Listing;
import entity.User;
import exception.CreateNewUserException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface UserSessionBeanLocal {

    public Long createNewUser(User user) throws CreateNewUserException;

    public List<User> retrieveAllUsers() throws EmptyListException;

    public User getUserByUserId(Long userId) throws EntityNotFoundException;

    public void updateUserDetails(Long userId, String firstName, String lastName, String email, String username, Date dob, String profilePic, Long contactNum) throws EntityNotFoundException;

    public void updateUserPassword(Long userId, String password) throws EntityNotFoundException;

    public void associateFollowingUserWithUser(User userToFollow, Long userId) throws EntityNotFoundException;

    public void deleteUser(Long userId) throws EntityNotFoundException;

    public void associateLikedListingWithUser(Listing listing, Long userId) throws EntityNotFoundException;

    public User retrieveUserByUsername(String username) throws EntityNotFoundException;

    public User userLogin(String username, String password) throws InvalidLoginCredentialsException;

    public List<User> retrieveAllNotDisabledUsers() throws EmptyListException;

    public void removeListingFromUserLikedListings(Long listingId) throws EmptyListException, EntityNotFoundException;

    public void updateUserDetails(User userToUpdate) throws EntityNotFoundException;
    
    public void follow(Long followingUserId, Long followedUserId) throws EntityNotFoundException;
    
    public void unfollow(Long followingUserId, Long followedUserId) throws EntityNotFoundException;

}
