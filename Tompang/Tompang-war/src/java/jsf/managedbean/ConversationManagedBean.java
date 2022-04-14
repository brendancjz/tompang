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
    private int buyerUnread;
    private int sellerUnread;

    public ConversationManagedBean() {
        showListingDetails = true;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("******* ConversationManagedBean.postConstruct()");
        try {
            newMessage = null;
            conversationToView = (Conversation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("conversation");
            if (conversationToView == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            }
            setBuyerUnread(conversationToView.getBuyerUnread());
            setSellerUnread(conversationToView.getSellerUnread());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Boolean messageIsSentByBuyer(Message message) {
        return conversationToView.getCreatedBy().getUserId() == message.getSentBy();
    }

    public List<Message> retrieveAllBuyerMessages() {
        System.out.println("*** ConversationManagedBean.retrieveAllBuyerMessages()");
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
        System.out.println("*** ConversationManagedBean.retrieveAllSellerMessages()");
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
        System.out.println("*** ConversationManagedBean.createNewMessage()");
        try {
            Message message = new Message();
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            if (isUserTheBuyer()) {
                message = new Message(newMessage, true, user.getUserId(), false);
            } else {
                message = new Message(newMessage, false, user.getUserId(), false);
            }
            Long messageId = getMessageSessionBean().createNewMessage(message);
            getConversationSessionBean().addMessage(conversationToView.getConvoId(), getMessageSessionBean().getMessageByMessageId(messageId));
            Conversation updatedConvo = getConversationSessionBean().getConversationByConvoId(conversationToView.getConvoId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", updatedConvo);
            this.postConstruct();
            
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void acceptOffer() {
        System.out.println("*** ConversationManagedBean.acceptOffer()");
        try {
            conversationSessionBean.updateMessageAndTransaction(conversationToView.getConvoId());
            conversationToView = conversationSessionBean.getConversationByConvoId(conversationToView.getConvoId());
            
            for(int i = 0; i<conversationToView.getMessages().size(); i++) {
                System.out.println(conversationToView.getMessages().get(i));
            }
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

    /**
     * @return the conversationSessionBean
     */
    public ConversationSessionBeanLocal getConversationSessionBean() {
        return conversationSessionBean;
    }

    /**
     * @param conversationSessionBean the conversationSessionBean to set
     */
    public void setConversationSessionBean(ConversationSessionBeanLocal conversationSessionBean) {
        this.conversationSessionBean = conversationSessionBean;
    }

    /**
     * @return the messageSessionBean
     */
    public MessageSessionBeanLocal getMessageSessionBean() {
        return messageSessionBean;
    }

    /**
     * @param messageSessionBean the messageSessionBean to set
     */
    public void setMessageSessionBean(MessageSessionBeanLocal messageSessionBean) {
        this.messageSessionBean = messageSessionBean;
    }

    /**
     * @return the buyerUnread
     */
    public int getBuyerUnread() {
        return buyerUnread;
    }

    /**
     * @param buyerUnread the buyerUnread to set
     */
    public void setBuyerUnread(int buyerUnread) {
        this.buyerUnread = buyerUnread;
    }

    /**
     * @return the sellerUnread
     */
    public int getSellerUnread() {
        return sellerUnread;
    }

    /**
     * @param sellerUnread the sellerUnread to set
     */
    public void setSellerUnread(int sellerUnread) {
        this.sellerUnread = sellerUnread;
    }

}
