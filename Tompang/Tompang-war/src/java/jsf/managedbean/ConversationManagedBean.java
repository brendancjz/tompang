/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import ejb.stateless.MessageSessionBeanLocal;
import entity.Conversation;
import entity.Message;
import entity.User;
import exception.EntityNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author brend
 */
@Named(value = "conversationManagedBean")
@ViewScoped
public class ConversationManagedBean implements Serializable {

    @EJB
    private ConversationSessionBeanLocal conversationSessionBean;

    @EJB
    private MessageSessionBeanLocal messageSessionBean;

    
    private Conversation conversationToView;
    private Boolean showListingDetails;
    
    private String newMessage;
    
    public ConversationManagedBean() {
        showListingDetails = true;
    }
    
    @PostConstruct
    public void postConstruct() {
        try {
            newMessage = null;
            conversationToView = (Conversation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("conversation");
            if (conversationToView == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Message> retrieveAllBuyerMessages() {
        List<Message> buyerMessages = conversationToView.getMessages();
        for (int i = 0; i < buyerMessages.size(); i++) {
            Message m = buyerMessages.get(i);
            if (!m.getFromBuyer()) {
                buyerMessages.remove(i);
                i--;
            }
        }
        
        return buyerMessages;
    }
    
    public List<Message> retrieveAllSellerMessages() {
        List<Message> buyerMessages = conversationToView.getMessages();
        for (int i = 0; i < buyerMessages.size(); i++) {
            Message m = buyerMessages.get(i);
            if (m.getFromBuyer()) {
                buyerMessages.remove(i);
                i--;
            }
        }
        
        return buyerMessages;
    }
    
    public Boolean isUserTheBuyer() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        
        return Objects.equals(conversationToView.getCreatedBy().getUserId(), user.getUserId());
    }
    
    public void createNewMessage() {
        try {
            Message message = new Message(newMessage, true);
            Long messageId = messageSessionBean.createNewMessage(message);
            
            conversationSessionBean.addMessage(conversationToView.getConvoId(), messageSessionBean.getMessageByMessageId(messageId));
            
            Conversation updatedConvo = conversationSessionBean.getConversationByConvoId(conversationToView.getConvoId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", updatedConvo);
            this.postConstruct();
        } catch (EntityNotFoundException ex) {
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

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }
    
    
    
}
