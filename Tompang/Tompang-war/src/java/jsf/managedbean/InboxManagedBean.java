/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import entity.Conversation;
import entity.Listing;
import entity.User;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author brend
 */
@Named(value = "inboxManagedBean")
@ViewScoped
public class InboxManagedBean implements Serializable {

    @EJB
    private ConversationSessionBeanLocal conversationSessionBean;

    private List<Conversation> buyerConversations;
    private List<Conversation> filteredBuyerConversations;

    private List<Conversation> sellerConversations;
    private List<Conversation> filteredSellerConversations;

    public InboxManagedBean() {
    }

    @PostConstruct
    public void retrieveConversations() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            buyerConversations = conversationSessionBean.retrieveAllBuyerConversations(user.getUserId());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            sellerConversations = conversationSessionBean.retrieveAllSellerConversations(user.getUserId());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void goToChat(AjaxBehaviorEvent event) throws IOException {

        Conversation convo = (Conversation) event.getComponent().getAttributes().get("conversation");

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");

    }

    public List<Conversation> getBuyerConversations() {
        return buyerConversations;
    }

    public void setBuyerConversations(List<Conversation> buyerConversations) {
        this.buyerConversations = buyerConversations;
    }

    public List<Conversation> getFilteredBuyerConversations() {
        return filteredBuyerConversations;
    }

    public void setFilteredBuyerConversations(List<Conversation> filteredBuyerConversations) {
        this.filteredBuyerConversations = filteredBuyerConversations;
    }

    public List<Conversation> getSellerConversations() {
        return sellerConversations;
    }

    public void setSellerConversations(List<Conversation> sellerConversations) {
        this.sellerConversations = sellerConversations;
    }

    public List<Conversation> getFilteredSellerConversations() {
        return filteredSellerConversations;
    }

    public void setFilteredSellerConversations(List<Conversation> filteredSellerConversations) {
        this.filteredSellerConversations = filteredSellerConversations;
    }

}
