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
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author GuoJun
 */
@Named(value = "viewAllListingsManagedBean")
@RequestScoped
public class ViewAllListingsManagedBean {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;
    
    private List<Listing> listings;
    
    public ViewAllListingsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            listings = listingSessionBean.retrieveAllListings();
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
    
}
