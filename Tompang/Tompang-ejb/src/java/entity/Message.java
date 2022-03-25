/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brend
 */
@Entity
public class Message implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdOn;
    @Column(nullable = false, length = 255)
    @NotNull
    private String body;
    @Column(nullable = false)
    @NotNull
    private Boolean fromBuyer;
    @Column(nullable = false)
    @NotNull
    private Long sentBy;
    @Column(nullable = false)
    @NotNull
    private Boolean readByBuyer;
    @Column(nullable = false)
    @NotNull
    private Boolean readBySeller;

    public Message() {
        this.createdOn = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Message(String body, Boolean fromBuyer, Long sentBy) {
        this();
        this.body = body;
        this.fromBuyer = fromBuyer;
        this.sentBy = sentBy;
        if (fromBuyer) {
            this.readByBuyer = true;
            this.readBySeller = false;
        } else {
            this.readBySeller = true;
            this.readByBuyer = false;
        }
    }

    public Long getMessageId() {
        return messageId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getBody() {
        return body;
    }

    public Boolean getFromBuyer() {
        return fromBuyer;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFromBuyer(Boolean fromBuyer) {
        this.fromBuyer = fromBuyer;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        Message m = (Message) o;
        return this.getCreatedOn().compareTo(m.getCreatedOn());
    }

    /**
     * @return the readByBuyer
     */
    public Boolean getReadByBuyer() {
        return readByBuyer;
    }

    /**
     * @param readByBuyer the readByBuyer to set
     */
    public void setReadByBuyer(Boolean readByBuyer) {
        this.readByBuyer = readByBuyer;
    }

    /**
     * @return the readBySeller
     */
    public Boolean getReadBySeller() {
        return readBySeller;
    }

    /**
     * @param readBySeller the readBySeller to set
     */
    public void setReadBySeller(Boolean readBySeller) {
        this.readBySeller = readBySeller;
    }

    /**
     * @return the sentBy
     */
    public Long getSentBy() {
        return sentBy;
    }

    /**
     * @param sentBy the sentBy to set
     */
    public void setSentBy(Long sentBy) {
        this.sentBy = sentBy;
    }
}
