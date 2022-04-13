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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author brend
 */
@Named(value = "likedListingsManagedBean")
@RequestScoped
public class LikedListingsManagedBean {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    
    private List<Listing> likedListings;
    
    public LikedListingsManagedBean() {
    }
    
    @PostConstruct
    public void retrieveLikedListings() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        
        likedListings = user.getLikedListings();
    }

    public void likeListing(AjaxBehaviorEvent event) {
        try {
            System.out.println("Like Listing method called.");
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            listingSessionBean.likeListing(user.getUserId(), listing.getListingId());

            //Update user in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveLikedListings(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void dislikeListing(AjaxBehaviorEvent event) {
        try {
            System.out.println("Dislike Listing method called.");
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            listingSessionBean.unlikeListing(user.getUserId(),listing.getListingId());
            
            //Update user in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveLikedListings(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void viewListing(AjaxBehaviorEvent event) throws IOException {
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listing);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }
    
    public void viewUserProfile(AjaxBehaviorEvent event) throws IOException {
        User user = (User) event.getComponent().getAttributes().get("user");
        System.out.print(user.getUsername());
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userToView", user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewUserProfile.xhtml");
    }
    
    public List<Listing> getLikedListings() {
        return likedListings;
    }

    public void setLikedListings(List<Listing> likedListings) {
        this.likedListings = likedListings;
    }
    
    
    
}
