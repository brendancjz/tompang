/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Dispute;

/**
 *
 * @author seanang
 */
public class CreateDisputeReq {
    
    private String username;
    private String password;
    private Long transactionId;
    private Dispute dispute;

    public CreateDisputeReq() {
    }

    public CreateDisputeReq(String username, String password, Long transactionId, Dispute dispute) {
        this.username = username;
        this.password = password;
        this.transactionId = transactionId;
        this.dispute = dispute;
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
     * @return the transactionId
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the dispute
     */
    public Dispute getDispute() {
        return dispute;
    }

    /**
     * @param dispute the dispute to set
     */
    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
    
    
    
    
    
}
