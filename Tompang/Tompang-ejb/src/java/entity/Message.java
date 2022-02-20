/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author brend
 */
@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private Date createdOn;
    private String body;
    private Boolean fromBuyer;

    public Message() {
    }

    public Message(Date createdOn, String body, Boolean fromBuyer) {
        this.createdOn = createdOn;
        this.body = body;
        this.fromBuyer = fromBuyer;
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
    
    
}
