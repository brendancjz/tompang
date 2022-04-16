/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Listing;
import entity.Transaction;
import entity.User;
import exception.CreateNewTransactionException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;
    
    private List<Double> list;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;
    
    
    
    @Override
    public Long createNewTransaction(Long buyerId, Long listingId, Transaction transaction) throws CreateNewTransactionException {
        if (buyerId == null || listingId == null){    
            throw new CreateNewTransactionException();
        }
        
        System.out.println(transaction.getBuyerCard());
        
        
        try {
            User buyer = userSessionBean.getUserByUserId(buyerId);
            Listing listing = listingSessionBean.getListingByListingId(listingId);
            buyer.getBuyerTransactions().add(transaction);
            listing.getTransactions().add(transaction);
            
            transaction.setBuyer(buyer);
            transaction.setListing(listing);
           
            
            System.out.println("Listing Quantity: " + listing.getQuantity());
            System.out.println("Transaction Quantity: " + transaction.getQuantity());
            
            Integer quantity = listing.getQuantity() - transaction.getQuantity();
            if(quantity == 0){
                listing.setIsOpen(false);
            } 
            listing.setQuantity(quantity);
            
        } catch(EntityNotFoundException ex){
            throw new CreateNewTransactionException();
        }
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
        //transaction.getSeller().getUserId();

        if (transaction == null) {
            throw new EntityNotFoundException("Transaction " + transactionId + " not found.\n");
        }

        return transaction;
    }
    
    @Override
    public void updateTransactionIsCompleted(Long transactionId, Boolean isCompleted) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        System.out.println(transaction.getSeller().getWalletAmount());
        transaction.getSeller().setWalletAmount(transaction.getSeller().getWalletAmount() + (transaction.getAmount() * 0.97));
        System.out.println(transaction.getSeller().getWalletAmount());
        transaction.setIsCompleted(isCompleted);
    }
    
       
    @Override
    public void updateTransactionIsAccepted(Long transactionId) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        System.out.println("TRANSACTION ACCEPTED");
        transaction.setIsAccepted(true);
    }
    
       
    @Override
    public void updateTransactionIsRejected(Long transactionId) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        System.out.println("TRANSACTION REJECTED");
        transaction.setIsRejected(true);
    }
    
    @Override
    public void updateTransactionHasDispute(Long transactionId, Boolean hasDispute) throws EntityNotFoundException {
        Transaction transaction = this.getTransactionByTransactionId(transactionId);
        
        transaction.setHasDispute(hasDispute);
    }
    
    public void deleteTransaction(Long transactionId) throws EntityNotFoundException{
        
       
       Transaction transactionToRemove = getTransactionByTransactionId(transactionId);
       
       
    }
    
    @Override
    public List<Transaction> retrieveTransactionsByUserId(Long userId){
        
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.buyer.userId = ?1 OR t.seller.userId = ?2");
        
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        
        return query.getResultList();
    
    }
    
    public List<Double> loadData(){
        this.setList(new ArrayList<Double>());
        List<Double> monthList = new ArrayList<Double>();
        
        for(int i = 0; i < 12; i++){
            Query query = em.createQuery("Select t.amount FROM Transaction t WHERE t.month = ?1 AND t.isCompleted = true");
            query.setParameter(1, i);
            monthList = query.getResultList();
            Double totalAmount = 0.0;
            if(!monthList.isEmpty()){
                for(Double amount: monthList){
                    totalAmount += amount;
                }
            } 
            list.add(totalAmount);
        }
        return list;
    }

    /**
     * @return the list
     */
    @Override
    public List<Double> getList() {
        return loadData();
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Double> list) {
        this.list = list;
    }
        
        
        
}
