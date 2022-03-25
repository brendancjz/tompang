/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import entity.Conversation;
import entity.User;
import exception.EmptyListException;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

/**
 *
 * @author brend
 */
@Named(value = "inboxManagedBean")
@ViewScoped
public class InboxManagedBean implements Serializable {

    @EJB
    private ConversationSessionBeanLocal conversationSessionBean;

    @Inject
    private ConversationManagedBean conversationManagedBean;

    private List<Conversation> buyerConversations;
    private List<Conversation> filteredBuyerConversations;

    private List<Conversation> sellerConversations;
    private List<Conversation> filteredSellerConversations;

    public InboxManagedBean() {
        this.buyerConversations = new ArrayList<>();
        this.sellerConversations = new ArrayList<>();
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

    public int getUnreadConversations() {
        int buyerUnread = 0;
        int sellerUnread = 0;
        if (!buyerConversations.isEmpty()) {
            for (int i = 0; i < buyerConversations.size(); i++) {
                buyerUnread += buyerConversations.get(i).getBuyerUnread();
            }
        }

        if (!sellerConversations.isEmpty()) {
            for (int i = 0; i < sellerConversations.size(); i++) {
                sellerUnread += sellerConversations.get(i).getSellerUnread();
            }
        }
        return buyerUnread + sellerUnread;
    }

    public void goToChat(AjaxBehaviorEvent event) throws IOException {

        Conversation convo = (Conversation) event.getComponent().getAttributes().get("conversation");
        User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        User postedBy = convo.getListing().getCreatedBy();
        if (currentUser.equals(postedBy)) {
            // seller
            System.out.println("SET TO 0");
            Integer index = sellerConversations.indexOf(convo);
            convo.setSellerUnread(0);
            conversationSessionBean.setSellerUnreadToZero(convo.getConvoId());
            sellerConversations.set(index, convo);
        } else {
            //buyer
            System.out.println("BUYER");
            Integer index = buyerConversations.indexOf(convo);
            convo.setBuyerUnread(0);
            conversationSessionBean.setBuyerUnreadToZero(convo.getConvoId());
            buyerConversations.set(index, convo);
        }
        System.out.println("BEFORE REDIRECTING");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");
        System.out.println("AFTER REDIRECTING");
    }

    public void redirectToInboxPage(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/userPages/inbox.xhtml");
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

    /**
     * @return the conversationManagedBean
     */
    public ConversationManagedBean getConversationManagedBean() {
        return conversationManagedBean;
    }

    /**
     * @param conversationManagedBean the conversationManagedBean to set
     */
    public void setConversationManagedBean(ConversationManagedBean conversationManagedBean) {
        this.conversationManagedBean = conversationManagedBean;
    }

}
