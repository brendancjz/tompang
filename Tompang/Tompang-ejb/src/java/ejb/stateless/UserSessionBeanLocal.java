package ejb.stateless;

import entity.Listing;
import entity.User;
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

    public Long createNewUser(User user);

    public List<User> retrieveAllUsers() throws EmptyListException;

    public User getUserByUserId(Long userId) throws EntityNotFoundException;

    public void updateUserDetails(Long userId, String firstName, String lastName, String email, String username, Date dob, Long contactNum) throws EntityNotFoundException;

    public void updateUserPassword(Long userId, String password) throws EntityNotFoundException;

    public void associateFollowingUserWithUser(User userToFollow, Long userId) throws EntityNotFoundException;

    public void deleteUser(Long userId) throws EntityNotFoundException;

    public void associateLikedListingWithUser(Listing listing, Long userId) throws EntityNotFoundException;

    public User retrieveUserByUsername(String username) throws EntityNotFoundException;

    public User userLogin(String username, String password) throws InvalidLoginCredentialsException;

    public List<User> retrieveAllNotDisabledUsers() throws EmptyListException;

    public void associateListingToUserLikedListings(Long userId, Long listingId) throws EntityNotFoundException;

    public void dissociateListingToUserLikedListings(Long userId, Long listingId) throws EntityNotFoundException;

}
