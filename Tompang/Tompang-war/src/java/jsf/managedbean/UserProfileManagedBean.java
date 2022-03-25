/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import entity.User;
import exception.EmptyListException;
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
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    private User userToView;
    private String username;
    private List<Listing> myListings;
    private List<Listing> userListings;
    
    public UserProfileManagedBean() {
    }
    
    @PostConstruct
    public void retrieveUser() {
        try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            userToView = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userToView");
            this.myListings = listingSessionBeanLocal.retrieveUserListings(user.getUsername());
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
    
    
    
}
