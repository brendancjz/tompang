/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Conversation;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author brend
 */
@Named(value = "conversationManagedBean")
@ViewScoped
public class ConversationManagedBean implements Serializable {

    private Conversation conversationToView;
    private Boolean showListingDetails;
    
    public ConversationManagedBean() {
        
    }
    
    @PostConstruct
    public void postConstruct() {
        try {
            showListingDetails = true;
            conversationToView = (Conversation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("conversation");
            if (conversationToView == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void toggleCollapseListingDetails() {
        showListingDetails = !showListingDetails;
        System.out.println("ShowListingDetails : " + showListingDetails);

    }

    public Conversation getConversationToView() {
        return conversationToView;
    }

    public void setConversationToView(Conversation conversationToView) {
        this.conversationToView = conversationToView;
    }

    public Boolean getShowListingDetails() {
        return showListingDetails;
    }

    public void setShowListingDetails(Boolean showListingDetails) {
        this.showListingDetails = showListingDetails;
    }
    
    
    
}
