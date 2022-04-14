/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.TransactionSessionBeanLocal;
import entity.Dispute;
import entity.Transaction;
import entity.User;
import exception.CreateNewDisputeException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import ejb.stateless.DisputeSessionBeanLocal;

/**
 *
 * @author seanang
 */
@Named(value = "viewMyTransactionsManagedBean")
@ViewScoped
public class ViewMyTransactionsManagedBean implements Serializable {

    @EJB
    private DisputeSessionBeanLocal disputeSessionBean;

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;
    
    private User user;
    private List<Transaction> transactions;
    private List<Transaction> filteredTransactions;
    private Long transactionIdOfDispute;
    private Dispute dispute;
    
    
    /**
     * Creates a new instance of ViewMyTransactionsManagedBean
     */
    public ViewMyTransactionsManagedBean() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        this.transactions = new ArrayList<>();
        this.dispute = new Dispute();
        
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("******* ViewMyTransactionsManagedBean.postConstruct()");
        this.setTransactions(transactionSessionBean.retrieveTransactionsByUserId(getUser().getUserId()));
    }
    
    public void raiseDispute(ActionEvent event){
       
        this.transactionIdOfDispute = (Long)event.getComponent().getAttributes().get("transactionId");
          
    }
    
    public void doRaiseDispute(){
        System.out.println("*** ViewMyTransactionsManagedBean.doRaiseDispute()");
        try{
            disputeSessionBean.createNewDispute(transactionIdOfDispute, dispute, this.user.getUserId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dispute raised successfully", null));
        } catch(CreateNewDisputeException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errro raising dispute", null));
        }
    }
    
    
    public List<Transaction> getFilteredTransactions() {
        return filteredTransactions;
    }

    /**
     * @param filteredTransactions the filteredTransactions to set
     */
    public void setFilteredTransactions(List<Transaction> filteredTransactions) {
        this.filteredTransactions = filteredTransactions;
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
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return the dispute
     */
    public Dispute getDispute() {
        return dispute;
    }

    /**
     * @param dispute the dispute to set
     */
    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
    
    
    
}
