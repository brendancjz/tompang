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
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author GuoJun
 */
@Named(value = "viewListingDetailsManagedBean")
@ViewScoped

public class ViewListingDetailsManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private Listing listingToView;

    public ViewListingDetailsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            listingToView = (Listing) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingToView");
            if (listingToView == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void likeListing(AjaxBehaviorEvent event) {
        try {
            System.out.println("Like Listing method called.");
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

            listingSessionBean.incrementListingLikes(listing.getListingId());
            userSessionBean.associateListingToUserLikedListings(user.getUserId(), listing.getListingId());

            //Update user in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listingSessionBean.getListingByListingId(listing.getListingId()));

            this.postConstruct();
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

            //Update user in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listingSessionBean.getListingByListingId(listing.getListingId()));
            this.postConstruct();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean showLikeButton() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        return user.getLikedListings().contains(listingToView);  
    }

    public Listing getListingToView() {
        return listingToView;
    }

    public void setListingToView(Listing listingToView) {
        this.listingToView = listingToView;
    }
}
