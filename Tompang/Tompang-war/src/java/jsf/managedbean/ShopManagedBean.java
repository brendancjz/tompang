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
import javax.inject.Named;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author brend
 */
@Named(value = "shopManagedBean")
@RequestScoped
public class ShopManagedBean {

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
        System.out.println("******* ShopManagedBean.retrieveAllListings()");
        try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

            this.listings = listingSessionBean.retrieveAllAvailableListings();
            this.userLikedListings = user.getLikedListings();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void likeListing(AjaxBehaviorEvent event) {
        System.out.println("*** ShopManagedBean.likeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

            listingSessionBean.likeListing(user.getUserId(),listing.getListingId());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveAllListings();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dislikeListing(AjaxBehaviorEvent event) {
        System.out.println("*** ShopManagedBean.dislikeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            listingSessionBean.unlikeListing(listing.getListingId(), user.getUserId());

            //Update user in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveAllListings();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewListing(AjaxBehaviorEvent event) throws IOException {
        System.out.println("*** ShopManagedBean.viewListing()");
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listing);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }
    
    public void viewUserProfile(AjaxBehaviorEvent event) throws IOException {
        System.out.println("*** ShopManagedBean.viewUserProfile()");
        User user = (User) event.getComponent().getAttributes().get("user");
        System.out.print(user.getUsername());
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userToView", user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewUserProfile.xhtml");
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
