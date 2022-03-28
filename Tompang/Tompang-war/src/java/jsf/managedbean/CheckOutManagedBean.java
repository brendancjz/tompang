/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.CreditCardSessionBeanLocal;
import ejb.stateless.TransactionSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Listing;
import entity.Transaction;
import entity.User;
import exception.CreateNewTransactionException;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private CreditCardSessionBeanLocal creditCardSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;
    
    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    private User user;
    private Listing listing;
    private long creditCardId;
    private CreditCard creditCard;
    private List<CreditCard> creditCards;
    private List<Long> creditCardNumbers;
    private Transaction transaction;

    private Boolean successfulCheckout;
    /**
     * Creates a new instance of CheckOutManagedBean
     */
    public CheckOutManagedBean() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        this.transaction = new Transaction();
        this.creditCards = new ArrayList<CreditCard>();
        this.creditCardNumbers = new ArrayList<Long>();
    }    
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("Checkout MB called");
        try {
            setListing((Listing) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listing"));
            if (getListing() == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("shop.xhtml");//Not confirmed yet
            } else {
                System.out.println(listing.getListingId());
            }

            this.setCreditCards(getUser().getCreditCards());
            for(CreditCard cc: this.creditCards){
                this.creditCardNumbers.add(cc.getCcNumber());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void checkOut(ActionEvent event){
        
        try{
           System.out.println("checkout called");
           this.getTransaction().setAmount(getListing().getPrice());
           this.getTransaction().setBuyer(getUser());
           this.getTransaction().setCreatedOn(new Date());
           this.getTransaction().setListing(getListing());
           this.getTransaction().setSeller(getListing().getCreatedBy());
           this.getTransaction().setCreditCard(creditCardSessionBean.getCreditCardByCCNumber(creditCardId));
           Long transactionId = transactionSessionBean.createNewTransaction(getUser().getUserId(), getListing().getListingId(), getTransaction());
           
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New transaction created successfully (Transaction ID: " + transactionId + ")", null));
        }catch(CreateNewTransactionException | EntityNotFoundException ex){
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


}
