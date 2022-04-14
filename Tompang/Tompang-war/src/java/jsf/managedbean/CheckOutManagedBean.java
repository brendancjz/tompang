/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ConversationSessionBeanLocal;
import ejb.stateless.CreditCardSessionBeanLocal;
import ejb.stateless.MessageSessionBeanLocal;
import ejb.stateless.TransactionSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.CreditCard;
import entity.Listing;
import entity.Message;
import entity.Transaction;
import entity.User;
import exception.CreateNewTransactionException;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author seanang
 */
@Named(value = "checkOutManagedBean")
@ViewScoped
public class CheckOutManagedBean implements Serializable {

    @EJB
    private MessageSessionBeanLocal messageSessionBean;

    @EJB
    private ConversationSessionBeanLocal conversationSessionBean;

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    private User user;
    private Listing listing;
    
    private Long creditCardNum;
    private CreditCard creditCard;
    private List<CreditCard> creditCards;
    private List<Long> creditCardNumbers;
    private Transaction transaction;

    private Boolean successfulCheckout;
    
    private Integer selectedQuantity;
    private List<Integer> quantitiesAvailable;

    /**
     * Creates a new instance of CheckOutManagedBean
     */
    public CheckOutManagedBean() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        this.transaction = new Transaction();
        this.creditCards = new ArrayList<>();
        this.creditCardNumbers = new ArrayList<>();
        this.quantitiesAvailable = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("******* CheckOutManagedBean.postConstruct()");
        try {
            setListing((Listing) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listing"));
            if (getListing() == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");
            } else {
                System.out.println(listing.getListingId());
            }

            this.setCreditCards(getUser().getCreditCards());
            for (CreditCard cc : this.creditCards) {
                this.getCreditCardNumbers().add(cc.getCcNumber());
            }
            
            for (int i = 1; i <= this.listing.getQuantity(); i++) {
                this.quantitiesAvailable.add(i);
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkOut(ActionEvent event) {
        System.out.println("*** CheckOutManagedBean.checkOut()");
        try {
            
            this.getTransaction().setAmount(getListing().getPrice());
            this.getTransaction().setBuyer(getUser());
            this.getTransaction().setCreatedOn(new Date());
            this.getTransaction().setListing(getListing());
            this.getTransaction().setSeller(getListing().getCreatedBy());
            this.getTransaction().setCreditCard(creditCardSessionBean.getCreditCardByCCNumber(getCreditCardNum()));
            this.getTransaction().setQuantity(selectedQuantity);
            Long transactionId = transactionSessionBean.createNewTransaction(getUser().getUserId(), getListing().getListingId(), getTransaction());
            Conversation convo;
            try {
                convo = conversationSessionBean.getUserConversationWithListing(getUser().getUserId(), getListing().getListingId());
            } catch (EntityNotFoundException ex) {
                System.out.println(ex.getMessage());
                convo = new Conversation(getUser(), getListing());
                conversationSessionBean.createNewConversation(convo, getListing().getListingId(), getUser().getUserId());
            }
            Message offerMessage = new Message(getUser().getFirstName() + " has offfered " + getListing().getPrice().toString() + ".", true, getUser().getUserId(), true);
            messageSessionBean.createNewMessage(offerMessage);
            conversationSessionBean.addMessage(convo.getConvoId(), offerMessage);
            Conversation updatedConvo = conversationSessionBean.getConversationByConvoId(convo.getConvoId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("conversation", updatedConvo);

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("conversation.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CheckOutManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New transaction created successfully (Transaction ID: " + transactionId + ")", null));
        } catch (CreateNewTransactionException | EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the listing
     */
    public Listing getListing() {
        return listing;
    }

    /**
     * @param listing the listing to set
     */
    public void setListing(Listing listing) {
        this.listing = listing;
    }

    /**
     * @return the creditCards
     */
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * @param creditCards the creditCards to set
     */
    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the creditCardId
     */
    public Long getCreditCardNum() {
        return creditCardNum;
    }

    /**
     * @param creditCardId the creditCardId to set
     */
    public void setCreditCardNum(Long creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    /**
     * @return the creditCard
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * @param creditCard the creditCard to set
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * @return the creditCardNumbers
     */
    public List<Long> getCreditCardNumbers() {
        return creditCardNumbers;
    }

    /**
     * @param creditCardNumbers the creditCardNumbers to set
     */
    public void setCreditCardNumbers(List<Long> creditCardNumbers) {
        this.creditCardNumbers = creditCardNumbers;
    }

    /**
     * @return the successfulCheckout
     */
    public Boolean getSuccessfulCheckout() {
        return successfulCheckout;
    }

    /**
     * @param successfulCheckout the successfulCheckout to set
     */
    public void setSuccessfulCheckout(Boolean successfulCheckout) {
        this.successfulCheckout = successfulCheckout;
    }

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public List<Integer> getQuantitiesAvailable() {
        return quantitiesAvailable;
    }

    public void setQuantitiesAvailable(List<Integer> quantitiesAvailable) {
        this.quantitiesAvailable = quantitiesAvailable;
    }

}
