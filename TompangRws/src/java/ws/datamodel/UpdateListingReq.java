/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Listing;

/**
 *
 * @author seanang
 */
public class UpdateListingReq {
    
    private String username;
    private String password;
    private Listing listing;

    public UpdateListingReq() {
    }

    public UpdateListingReq(String username, String password, Listing listing) {
        this.username = username;
        this.password = password;
        this.listing = listing;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the listing
     */
    public Listing getListing() {
        return listing;
    }

    /**
     * @param listing the listing to set
     */
    public void setListing(Listing listing) {
        this.listing = listing;
    }
    
    
    
}
