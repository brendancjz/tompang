/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author seanang
 */
@Entity
public class Dispute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disputeId;
    @OneToOne(mappedBy = "transaction")
    private Transaction transaction;
    @Column(nullable = false, length = 100)
    @NotNull
    private String description;
    @Column(nullable = false)
    @NotNull
    private boolean isResolved;

    public Dispute() {
    }

    public Dispute(String description) {
        this.description = description;
        this.isResolved = false;
    }

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(Long disputeId) {
        this.disputeId = disputeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disputeId != null ? disputeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the disputeId fields are not set
        if (!(object instanceof Dispute)) {
            return false;
        }
        Dispute other = (Dispute) object;
        if ((this.disputeId == null && other.disputeId != null) || (this.disputeId != null && !this.disputeId.equals(other.disputeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dispute[ id=" + disputeId + " ]";
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isResolved
     */
    public boolean isIsResolved() {
        return isResolved;
    }

    /**
     * @param isResolved the isResolved to set
     */
    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }
    
}
