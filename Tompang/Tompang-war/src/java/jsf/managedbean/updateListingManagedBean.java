/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author GuoJun
 */
@Named(value = "updateListingManagedBean")
@ViewScoped
public class updateListingManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private Long listingIdToUpdate;
    private Listing listingToUpdate;
    
    
    public updateListingManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        listingIdToUpdate = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToUpdate");
        
        try {
            if (listingIdToUpdate != null) {
                listingToUpdate = listingSessionBeanLocal.getListingByListingId(listingIdToUpdate);
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No listing has been selected", null));
            }
         }
        catch(EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the listing details: " + ex.getMessage(), null));
        }
        catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void phaseListener(PhaseEvent event) {
    }

    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToUpdate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");

    }
    
    public void updateListing() {
        try {
            listingSessionBeanLocal.updateListingDetails(listingIdToUpdate, listingToUpdate.getCountry(), listingToUpdate.getCity(), listingToUpdate.getTitle(), listingToUpdate.getDescription(), 0, listingToUpdate.getExpectedArrivalDate(), 0, true, listingToUpdate.getPhotos());
        } 
        catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating listing: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public Listing getListingToUpdate() {
        return listingToUpdate;
    }

    public void setListingToUpdate(Listing listingToUpdate) {
        this.listingToUpdate = listingToUpdate;
    }
    
    
    
}
