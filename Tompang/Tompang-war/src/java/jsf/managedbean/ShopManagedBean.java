/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
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
    private ConversationSessionBeanLocal conversationSessionBean;

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
            this.userLikedListings = user.getLikedListings();
        } catch (EmptyListException ex) {
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
            this.retrieveAllListings();
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
            this.retrieveAllListings();
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

    public void goToChat(AjaxBehaviorEvent event) throws IOException {

        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        
        Conversation convo;
        try {
            convo = conversationSessionBean.getUserConversationWithListing(user.getUserId(), listing.getListingId());
            
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
            convo = new Conversation(user, listing);
            conversationSessionBean.createNewConversation(convo, user.getUserId(), listing.getListingId());
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");

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
