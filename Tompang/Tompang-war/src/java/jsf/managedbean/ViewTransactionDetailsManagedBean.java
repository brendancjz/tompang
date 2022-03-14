/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Transaction;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

/**
 *
 * @author ignit
 */
@Named(value = "viewTransactionDetailsManagedBean")
@ViewScoped
public class ViewTransactionDetailsManagedBean implements Serializable {

    private Transaction transactionToView;
    /**
     * Creates a new instance of ViewTransactionDetailsManagedBean
     */
    public ViewTransactionDetailsManagedBean() {
        transactionToView = new Transaction();
    }

    /**
     * @return the transcationToView
     */
    public Transaction getTranscationToView() {
        return transactionToView;
    }

    /**
     * @param transcationToView the transcationToView to set
     */
    public void setTranscationToView(Transaction transcationToView) {
        System.out.println("Set method called");
        this.transactionToView = transcationToView;
    }
    
}
