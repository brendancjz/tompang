/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author brend
 */
@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(nullable = false)
    @NotNull
    @Digits(integer = 6, fraction = 2)
    @PositiveOrZero
    private Double amount;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdOn;
    @Column(nullable = false)
    @NotNull
    private Boolean isCompleted;
    @Column(nullable = false)
    @NotNull
    private Boolean hasDispute;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User buyer;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User seller;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Listing listing;
    @OneToOne
    private Dispute dispute;
    @Column(nullable = false)
    @NotNull
    private Integer quantity;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CreditCard creditCard;
    @Column(nullable = false)
    @NotNull
    private Boolean isAccepted;
    @Column(nullable = false)
    @NotNull
    private Boolean isRejected;
    private Integer month;

    public Transaction() {
        this.isCompleted = false;
        this.hasDispute = false;
        this.isAccepted = false;
        this.isRejected = false;
    }

    public Transaction(Double amount, Date createdOn, User buyer, User seller, Listing listing, Integer quantity, CreditCard buyerCard) {
        this();
        this.amount = amount;
        this.createdOn = createdOn;
        this.buyer = buyer;
        this.seller = seller;
        this.listing = listing;
        this.quantity = quantity;
        this.creditCard = buyerCard;
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdOn);
        this.month = cal.get(Calendar.MONTH);
        
    }
    
    public String disputeStatus(){
        if(dispute != null){
            if(dispute.isIsResolved()){
                return "Resolved";
            } else {
                return "Ongoing";
            }
            
        } else {
            return "None";
        }
        
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }
    
    public Long getTransactionId() {
        return transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public Boolean getHasDispute() {
        return hasDispute;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Listing getListing() {
        return listing;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setHasDispute(Boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
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

    /**
     * @return the isAccepted
     */
    public Boolean getIsAccepted() {
        return isAccepted;
    }

    /**
     * @param isAccepted the isAccepted to set
     */
    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    /**
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
