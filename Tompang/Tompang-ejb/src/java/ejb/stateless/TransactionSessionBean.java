/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Transaction;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brend
 */
@Stateless
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewTransaction(Transaction transaction) {
        em.persist(transaction);
        em.flush();

        return transaction.getTransactionId();
    }

    @Override
    public List<Transaction> retrieveAllTransactions() throws EmptyListException {
        Query query = em.createQuery("SELECT t FROM Transaction t");

        List<Transaction> transactions = query.getResultList();

        if (transactions.isEmpty()) {
            throw new EmptyListException("List of transactions is empty.\n");
        }

        return transactions; 
    }

    @Override
    public Transaction getTransactionByTransactionId(Long transactionId) throws EntityNotFoundException {
        Transaction transaction = em.find(Transaction.class, transactionId);

        if (transaction == null) {
            throw new EntityNotFoundException("Transaction " + transactionId + " not found.\n");
        }

        return transaction;
    }
    
    @Override
    public void updateTransactionIsCompleted(Long transactionId, Boolean isCompleted) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        
        transaction.setIsCompleted(isCompleted);
    }
    
    @Override
    public void updateTransactionHasDispute(Long transactionId, Boolean hasDispute) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        
        transaction.setHasDispute(hasDispute);
    }
    
}
