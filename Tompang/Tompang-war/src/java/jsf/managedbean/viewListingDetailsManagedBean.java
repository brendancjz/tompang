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
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author GuoJun
 */
@Named(value = "viewListingDetailsManagedBean")
@ViewScoped

public class viewListingDetailsManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    private Long listingIdToView;
    private String backMode;
    private Listing listingToView;

    
    public viewListingDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        listingIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("listingIdToView");
        backMode = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backMode");
        
        try {
            if (listingIdToView != null) {
                    setListingToView(listingSessionBeanLocal.getListingByListingId(listingIdToView));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No listing has been selected", null));
            }
        }
        catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void phaseListener(PhaseEvent event)
    {        
    }
    
    public void back(ActionEvent event) throws IOException
    {
        if(backMode == null || backMode.trim().length() == 0)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllListings.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(backMode + ".xhtml");
        }
    }
    
    public void updateListing(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToUpdate", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateListing.xhtml");
    }
    
    public void deleteListing(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToDelete", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteListing.xhtml");
    }

    public Listing getListingToView() {
        return listingToView;
    }

    public void setListingToView(Listing listingToView) {
        this.listingToView = listingToView;
    }
}
