/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Transaction;

/**
 *
 * @author seanang
 */
public class CreateTransactionReq {
    
    private String username;
    private String password;
    private Long listingId;
    private Transaction transaction;

    public CreateTransactionReq() {
    }

    public CreateTransactionReq(String username, String password, Long listingId, Transaction transaction) {
        this.username = username;
        this.password = password;
        this.listingId = listingId;
        this.transaction = transaction;
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
     * @return the listingId
     */
    public Long getListingId() {
        return listingId;
    }

    /**
     * @param listingId the listingId to set
     */
    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    
    
}
