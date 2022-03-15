/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brend
 */
@Entity
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long convoId;
    @Column(nullable = false)
    @NotNull
    private Boolean isOpen;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Listing listing;
    @OneToMany
    private List<Message> messages;

    public Conversation() {
        this.messages = new ArrayList<>();
        this.isOpen = true;
    }

    public Conversation(User createdBy, Listing listing) {
        this();
        this.createdBy = createdBy;
        this.listing = listing;
    }

    public Long getConvoId() {
        return convoId;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Listing getListing() {
        return listing;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setConvoId(Long convoId) {
        this.convoId = convoId;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.convoId == null && other.convoId != null) || (this.convoId != null && !this.convoId.equals(other.convoId))) {
            return false;
        }
        return true;
    }
    
}
