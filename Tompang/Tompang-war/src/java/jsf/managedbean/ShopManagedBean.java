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
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author brend
 */
@Named(value = "shopManagedBean")
@ViewScoped
public class ShopManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private List<Listing> listings;
    private List<Listing> userLikedListings;
    
    public ShopManagedBean() {
    }
    
    @PostConstruct
    public void retrieveAllListings() {
        try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            this.listings = listingSessionBean.retrieveAllAvailableListings();
            this.userLikedListings =  user.getLikedListings();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void likeListing(ActionEvent event) {
        try {
            System.out.println("Like Listing method called.");
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            listingSessionBean.incrementListingLikes(listing.getListingId());
            userSessionBean.associateListingToUserLikedListings(user.getUserId(), listing.getListingId());
            this.userLikedListings.add(listing);
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void dislikeListing(AjaxBehaviorEvent event) {
        try {
            System.out.println("Dislike Listing method called.");
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            listingSessionBean.decrementListingLikes(listing.getListingId());
            userSessionBean.dissociateListingToUserLikedListings(user.getUserId(), listing.getListingId());
            this.userLikedListings.remove(listing); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getUserLikedListings() {
        return userLikedListings;
    }

    public void setUserLikedListings(List<Listing> userLikedListings) {
        this.userLikedListings = userLikedListings;
    }
    
}
