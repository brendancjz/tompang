/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Listing;
import entity.User;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author GuoJun
 */
@Named(value = "userProfileManagedBean")
@RequestScoped
public class UserProfileManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    private User currentUser;
    
    private User userToView;
    private List<User> userToViewFollowing;
    private List<User> userToViewFollowers;
    
    private String username;
    private List<Listing> myListings;
    private List<Listing> userListings;
    
    public UserProfileManagedBean() {
    }
    
    @PostConstruct
    public void retrieveUser() {
        try {
            currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            userToView = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userToView");
            this.userToViewFollowing = userToView.getFollowing();
            this.userToViewFollowers = userToView.getFollowers();
            this.myListings = listingSessionBeanLocal.retrieveUserListings(currentUser.getUsername());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void retrieveUserListings(AjaxBehaviorEvent event) {
        try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.userListings = listingSessionBeanLocal.retrieveUserListings(user.getUsername());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
    public void viewProfile(AjaxBehaviorEvent event) throws IOException {
        User user = (User) event.getComponent().getAttributes().get("user");
        username = user.getUsername();
        System.out.print(user.getUsername());
        System.out.print(username);
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userToView", user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("userListings.xhtml");
    }
    
    public void followUser(AjaxBehaviorEvent event) {
        try {
            User userToFollow = (User) event.getComponent().getAttributes().get("user");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            userSessionBean.follow(user.getUserId(), userToFollow.getUserId());
            
            //Update user to view in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userToView", userSessionBean.getUserByUserId(userToView.getUserId()));
            this.retrieveUser(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void unfollowUser(AjaxBehaviorEvent event) {
        try {
            User userToUnfollow = (User) event.getComponent().getAttributes().get("user");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            userSessionBean.unfollow(user.getUserId(), userToUnfollow.getUserId());
            
            //Update users to view in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userToView", userSessionBean.getUserByUserId(userToView.getUserId()));
            this.retrieveUser(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Listing> getMyListings() {
        return myListings;
    }

    public void setMyListings(List<Listing> myListings) {
        this.myListings = myListings;
    }

    public List<Listing> getUserListings() {
        return userListings;
    }

    public void setUserListings(List<Listing> userListings) {
        this.userListings = userListings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUserToView() {
        return userToView;
    }

    public void setUserToView(User userToView) {
        this.userToView = userToView;
    }

    public List<User> getUserToViewFollowing() {
        return userToViewFollowing;
    }

    public void setUserToViewFollowing(List<User> userToViewFollowing) {
        this.userToViewFollowing = userToViewFollowing;
    }

    public List<User> getUserToViewFollowers() {
        return userToViewFollowers;
    }

    public void setUserToViewFollowers(List<User> userToViewFollowers) {
        this.userToViewFollowers = userToViewFollowers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    
    
}
