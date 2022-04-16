/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CreditCard;

/**
 *
 * @author seanang
 */
public class CreateCreditCardReq {
    
    private String username;
    private String password;
    private CreditCard creditCard;

    public CreateCreditCardReq() {
    }

    public CreateCreditCardReq(String username, String password, CreditCard creditCard) {
        this.username = username;
        this.password = password;
        this.creditCard = creditCard;
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
     * @return the creditCard
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * @param creditCard the creditCard to set
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    
    
    
}
