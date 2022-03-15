/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;


/**
 *
 * @author GuoJun
 */
@Named(value = "viewAllListingsManagedBean")
@ViewScoped
public class ViewAllListingsManagedBean implements Serializable {

    
    @EJB
    private ListingSessionBeanLocal listingSessionBean;
    
    @Inject
    private ViewListingDetailsEzCompManagedBean viewListingDetailsEzCompManagedBean;
    
    private List<Listing> listings;
    private List<Listing> filteredListings;
    
    private String filteredUsername;
    public ViewAllListingsManagedBean() {
    }

    @PostConstruct
    public void retrieveAllListings() {
        listings = new ArrayList<>();
        
        System.out.println("PostConstruct for ViewAllListingManagedBean called.");
        try {
            //This is for when admin wishes to view User's Listings from the View User Details
            filteredUsername = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("username");
            
            if (filteredUsername == null) {
                listings = listingSessionBean.retrieveAllListings();
            } else {
                listings = listingSessionBean.retrieveUserListings(filteredUsername);
            }

        } catch (EmptyListException ex) {
            System.out.println("List of listings empty.");
        }
    }
    
//    public void viewListingDetails(ActionEvent event) throws IOException {
//        Long listingIdToView = (Long)event.getComponent().getAttributes().get("listingId");
//        System.err.print(listingIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
//    }
    
    public void deleteListing(ActionEvent event) {
        try {
            System.out.println("Deleting Listing in ViewAllListingsManagedBean");
            
            Listing listingToDelete = (Listing) event.getComponent().getAttributes().get("listingToDelete");
            
            listingSessionBean.deleteListing(listingToDelete.getListingId());
            
            try {
                Listing listing = listingSessionBean.getListingByListingId(listingToDelete.getListingId());
                //Means listing is disabled, not deleted.
                this.retrieveAllListings();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing disabled successfully", null));
            } catch (EntityNotFoundException ex) {
                listings.remove(listingToDelete);

                if (filteredListings != null) {
                    filteredListings.remove(listingToDelete);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing deleted successfully", null));
            }} catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting listing: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public Integer getNumberOfListings() {
        
        try {
            return listingSessionBean.retrieveAllListings().size();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
    
    public String getCreatedOn(Listing listing) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(listing.getCreatedOn());
    }
    
    public String getArrivalDate(Listing listing) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(listing.getExpectedArrivalDate());
    }
    
    public List<Listing> getListings() {
        return listings;
    }

    public void setListOfListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getFilteredListings() {
        return filteredListings;
    }

    public void setFilteredListings(List<Listing> filteredListings) {
        this.filteredListings = filteredListings;
    }

    public String getFilteredUsername() {
        return filteredUsername;
    }

    public void setFilteredUsername(String filteredUsername) {
        this.filteredUsername = filteredUsername;
    }

    public ViewListingDetailsEzCompManagedBean getViewListingDetailsEzCompManagedBean() {
        return viewListingDetailsEzCompManagedBean;
    }

    public void setViewListingDetailsEzCompManagedBean(ViewListingDetailsEzCompManagedBean viewListingDetailsEzCompManagedBean) {
        this.viewListingDetailsEzCompManagedBean = viewListingDetailsEzCompManagedBean;
    }

    

    
    
}
