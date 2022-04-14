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
@Named(value = "myListingsManagedBean")
@RequestScoped
public class MyListingsManagedBean {
    @EJB
    private ListingSessionBeanLocal listingSessionBean;
    @EJB
    private UserSessionBeanLocal userSessionBean;
    private List<Listing> myListings;
    private List<Listing> userLikedListings;
    
    /**
     * Creates a new instance of MyListingsManagedBean
     */
    public MyListingsManagedBean() {
    }
    
    @PostConstruct
    public void retrieveMyListings() {
        System.out.println("******* MyListingsManagedBean.retrieveMyListings()");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        this.userLikedListings = user.getLikedListings();
        myListings = user.getCreatedListings();
    }

    public void likeListing(AjaxBehaviorEvent event) {
        System.out.println("*** MyListingsManagedBean.likeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            listingSessionBean.likeListing(user.getUserId(),listing.getListingId());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveMyListings(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void dislikeListing(AjaxBehaviorEvent event) {
        System.out.println("*** MyListingsManagedBean.dislikeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            listingSessionBean.unlikeListing(listing.getListingId(), user.getUserId());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveMyListings(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void viewListing(AjaxBehaviorEvent event) throws IOException {
        System.out.println("*** MyListingsManagedBean.viewListing()");
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listing);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }
    
    public List<Listing> getMyListings() {
        return myListings;
    }

    public void setMyListings(List<Listing> myListings) {
        this.myListings = myListings;
    }

    public List<Listing> getUserLikedListings() {
        return userLikedListings;
    }

    public void setUserLikedListings(List<Listing> userLikedListings) {
        this.userLikedListings = userLikedListings;
    }
    
    
    
}
