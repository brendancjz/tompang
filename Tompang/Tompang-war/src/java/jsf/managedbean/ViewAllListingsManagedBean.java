/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import exception.EmptyListException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    private ViewListingDetailsManagedBean viewListingDetailsManagedBean;
    
    private List<Listing> listings;
    private List<Listing> filteredListings;
    
    private String filteredUsername;
    public ViewAllListingsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
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
    
    public void viewListingDetails(ActionEvent event) throws IOException {
        Long listingIdToView = (Long)event.getComponent().getAttributes().get("listingId");
        System.err.print(listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
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

    
    
}
