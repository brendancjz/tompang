/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Transaction;
import exception.CreateNewTransactionException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface TransactionSessionBeanLocal {

    public Long createNewTransaction(Long buyerId,Long listingId, Transaction transaction) throws CreateNewTransactionException;

    public List<Transaction> retrieveAllTransactions() throws EmptyListException;

    public Transaction getTransactionByTransactionId(Long transactionId) throws EntityNotFoundException;

    public void updateTransactionIsCompleted(Long transactionId, Boolean isCompleted) throws EntityNotFoundException;

    public void updateTransactionHasDispute(Long transactionId, Boolean hasDispute) throws EntityNotFoundException;
    
}
