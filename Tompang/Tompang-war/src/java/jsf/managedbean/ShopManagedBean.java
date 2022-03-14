/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import exception.EmptyListException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

/**
 *
 * @author brend
 */
@Named(value = "shopManagedBean")
@ViewScoped
public class ShopManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private List<Listing> listings;
    
    public ShopManagedBean() {
    }
    
    @PostConstruct
    public void retrieveAllListings() {
        try {
            this.listings = listingSessionBean.retrieveAllAvailableListings();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
    
}
