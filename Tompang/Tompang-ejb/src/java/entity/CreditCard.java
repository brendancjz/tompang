/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author brend
 */
@Entity
public class CreditCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ccId;
    private String ccName;
    private Long ccNumber;
    private Integer ccCIV;
    private String expiryDate;

    public CreditCard() {
    }

    public CreditCard(String ccName, Long ccNumber, Integer ccCIV, String expiryDate) {
        this();
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.ccCIV = ccCIV;
        this.expiryDate = expiryDate;
    }

    public Long getCcId() {
        return ccId;
    }

    public String getCcName() {
        return ccName;
    }

    public Long getCcNumber() {
        return ccNumber;
    }

    public Integer getCcCIV() {
        return ccCIV;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setCcId(Long ccId) {
        this.ccId = ccId;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public void setCcNumber(Long ccNumber) {
        this.ccNumber = ccNumber;
    }

    public void setCcCIV(Integer ccCIV) {
        this.ccCIV = ccCIV;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    
    
}
