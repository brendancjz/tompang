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

    private List<Conversation> conversations;
    private List<Conversation> filteredConversations;

    public InboxManagedBean() {
    }

    @PostConstruct
    public void retrieveConversations() {
        try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            conversations = conversationSessionBean.retrieveAllUserConversations(user.getUserId());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void goToChat(AjaxBehaviorEvent event) throws IOException {

        Conversation convo = (Conversation) event.getComponent().getAttributes().get("conversation");
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");

    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<Conversation> getFilteredConversations() {
        return filteredConversations;
    }

    public void setFilteredConversations(List<Conversation> filteredConversations) {
        this.filteredConversations = filteredConversations;
    }

}
