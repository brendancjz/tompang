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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author ignit
 */
@Named(value = "viewAllConversationsManagedBean")
@ViewScoped
public class ViewAllConversationsManagedBean implements Serializable {

    @EJB
    private ConversationSessionBeanLocal conversationSessionBeanLocal;

    private List<Conversation> conversations;
    private List<Conversation> filteredConversations;

    /**
     * Creates a new instance of ViewAllConversationsManagedBean
     */
    public ViewAllConversationsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("******* ViewAllConversationsManagedBean.retrieveAllConversations()");
        try {
            this.conversations = conversationSessionBeanLocal.retrieveAllConversations();
        } catch (EmptyListException ex) {
            System.out.println("Unable to retrieve list of conversation");
        }
    }

    public void goToChat(AjaxBehaviorEvent event) throws IOException {
        System.out.println("*** ViewAllConversationsManagedBean.goToChat()");

        Conversation convo = (Conversation) event.getComponent().getAttributes().get("conversation");
//        User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
//        User postedBy = convo.getListing().getCreatedBy();
//        if (currentUser.equals(postedBy)) {
//            // Seller
//            Integer index = sellerConversations.indexOf(convo);
//            convo.setSellerUnread(0);
//            conversationSessionBean.setSellerUnreadToZero(convo.getConvoId());
//            sellerConversations.set(index, convo);
//        } else {
//            // Buyer
//            Integer index = buyerConversations.indexOf(convo);
//            convo.setBuyerUnread(0);
//            conversationSessionBean.setBuyerUnreadToZero(convo.getConvoId());
//            buyerConversations.set(index, convo);
//        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("../userPages/conversation.xhtml");
    }

    /**
     * @return the conversations
     */
    public List<Conversation> getConversations() {
        return conversations;
    }

    /**
     * @param conversations the conversations to set
     */
    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    /**
     * @return the filteredConversations
     */
    public List<Conversation> getFilteredConversations() {
        return filteredConversations;
    }

    /**
     * @param filteredConversations the filteredConversations to set
     */
    public void setFilteredConversations(List<Conversation> filteredConversations) {
        this.filteredConversations = filteredConversations;
    }

}
