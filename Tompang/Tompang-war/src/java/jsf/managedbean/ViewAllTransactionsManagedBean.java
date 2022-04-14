/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.TransactionSessionBeanLocal;
import entity.Transaction;
import exception.EmptyListException;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author ignit
 */
@Named(value = "viewAllTransactionsManagedBean")
@ViewScoped
public class ViewAllTransactionsManagedBean implements Serializable {

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    @Inject
    private ViewTransactionDetailsManagedBean viewTransactionDetailsManagedBean;

    private List<Transaction> listOfTransactions;
    private List<Transaction> filteredTransactions;

    /**
     * Creates a new instance of ViewAllTransactionManagedBean
     */
    public ViewAllTransactionsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("******* ViewAllTransactionsManagedBean.postConstruct()");
        try {
            setListOfTransactions(transactionSessionBean.retrieveAllTransactions());
        } catch (EmptyListException ex) {
            System.out.println("No Transaction Records Found");
        }
    }

    public void viewTransactionDetails(ActionEvent event) throws IOException {
        System.out.println("*** ViewAllTransactionsManagedBean.viewTransactionDetails()");
        Long transactionIdToView = (Long) event.getComponent().getAttributes().get("transactionId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("transactionIdToView", transactionIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewTransactionDetails.xhtml");
    }

    /**
     * @return the listOfTransactions
     */
    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    /**
     * @param listOfTransactions the listOfTransactions to set
     */
    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }

    /**
     * @return the filteredTransactions
     */
    public List<Transaction> getFilteredTransactions() {
        return filteredTransactions;
    }

    /**
     * @param filteredTransactions the filteredTransactions to set
     */
    public void setFilteredTransactions(List<Transaction> filteredTransactions) {
        this.filteredTransactions = filteredTransactions;
    }

    public ViewTransactionDetailsManagedBean getViewTransactionDetailsManagedBean() {
        return viewTransactionDetailsManagedBean;
    }

    public void setViewTransactionDetailsManagedBean(ViewTransactionDetailsManagedBean viewTransactionDetailsManagedBean) {
        this.viewTransactionDetailsManagedBean = viewTransactionDetailsManagedBean;
    }
    
    
}
