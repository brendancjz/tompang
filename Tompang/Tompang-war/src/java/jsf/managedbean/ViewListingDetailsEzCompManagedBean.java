/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Listing;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author brend
 */
@Named(value = "viewListingDetailsEzCompManagedBean")
@ViewScoped
public class ViewListingDetailsEzCompManagedBean implements Serializable {

    private Listing listingToView;
    
    public ViewListingDetailsEzCompManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        
    }

    public Listing getListingToView() {
        return listingToView;
    }

    public void setListingToView(Listing listingToView) {
        this.listingToView = listingToView;
    }
    
    
}
