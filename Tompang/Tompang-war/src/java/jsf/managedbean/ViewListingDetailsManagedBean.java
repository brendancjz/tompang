package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.Listing;
import entity.User;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author GuoJun
 */
@Named(value = "viewListingDetailsManagedBean")
@ViewScoped

public class ViewListingDetailsManagedBean implements Serializable {

    @EJB
    private ConversationSessionBeanLocal conversationSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private Listing listingToView;

    public ViewListingDetailsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("******* ViewListingDetailsManagedBean.postConstruct()");
        try {
            listingToView = (Listing) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingToView");
            if (listingToView == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void likeListing(AjaxBehaviorEvent event) {
        System.out.println("*** ViewListingDetailsManagedBean.likeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

            listingSessionBean.likeListing(user.getUserId(), listing.getListingId());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listingSessionBean.getListingByListingId(listing.getListingId()));

            this.postConstruct();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dislikeListing(AjaxBehaviorEvent event) {
        System.out.println("*** ViewListingDetailsManagedBean.likeListing()");
        try {
            Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            listingSessionBean.unlikeListing(user.getUserId(), listing.getListingId());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", listingSessionBean.getListingByListingId(listing.getListingId()));
            this.postConstruct();
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void goToChat(AjaxBehaviorEvent event) throws IOException {
        System.out.println("*** ViewListingDetailsManagedBean.goToChat()");
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

        Conversation convo;
        try {
            convo = conversationSessionBean.getUserConversationWithListing(user.getUserId(), listing.getListingId());

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
            convo = new Conversation(user, listing);
            conversationSessionBean.createNewConversation(convo, listing.getListingId(), user.getUserId());
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", convo);
        FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");

    }

    public void checkOut(ActionEvent event) throws IOException {
        System.out.println("*** ViewListingDetailsManagedBean.checkOut()");
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listing", listing);
        FacesContext.getCurrentInstance().getExternalContext().redirect("checkout.xhtml");

    }

    public Boolean didUserCreateThisListing() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        return Objects.equals(user.getUserId(), listingToView.getCreatedBy().getUserId());
    }

    public boolean showLikeButton() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        return user.getLikedListings().contains(listingToView);
    }

    public Listing getListingToView() {
        return listingToView;
    }

    public void setListingToView(Listing listingToView) {
        this.listingToView = listingToView;
    }
}
