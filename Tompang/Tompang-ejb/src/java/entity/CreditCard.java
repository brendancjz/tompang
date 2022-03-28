/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    @Column(nullable = false, length = 100)
    @NotNull
    private String ccName;
    @Column(nullable = false, length = 16)
    @NotNull
    @Digits(integer=16, fraction=0)
    @Positive
    private Long ccNumber;
    @Column(nullable = false, length = 3)
    @NotNull
    @Digits(integer=3, fraction=0)
    @Positive
    private Integer ccCIV;
    @Column(nullable = false)
    @NotNull
    @Future
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expiryDate;

    public CreditCard() {
    }

    public CreditCard(String ccName, Long ccNumber, Integer ccCIV, Date expiryDate) {
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

    public Date getExpiryDate() {
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

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) object;
        if ((this.ccId == null && other.ccId != null) || (this.ccId != null && !this.ccId.equals(other.ccId))) {
            return false;
        }
        return true;
    }

    
  
    
    @Override
    public String toString() {
        return Long.toString(this.ccNumber);
    }
    
}
