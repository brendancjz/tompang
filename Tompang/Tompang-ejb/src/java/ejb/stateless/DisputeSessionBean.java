/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Dispute;
import entity.Transaction;
import exception.CreateNewDisputeException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author seanang
 */
@Stateless
public class DisputeSessionBean implements DisputeSessionBeanLocal {

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;
    
    

    @Override
    public Long createNewDispute(Long transactionId, Dispute dispute) throws CreateNewDisputeException{
        
        if(transactionId == null){
            throw new CreateNewDisputeException();
        }
        
        try {
            
            Transaction transaction = transactionSessionBean.getTransactionByTransactionId(transactionId);
            transaction.setDispute(dispute);
            transaction.setHasDispute(true);
            dispute.setTransaction(transaction);
            
            
        } catch(EntityNotFoundException ex){
            throw new CreateNewDisputeException();
        }
        
        em.persist(dispute);
        em.flush();
        
        return dispute.getDisputeId();
    }

    @Override
    public List<Dispute> retrieveAllDisputes() throws EmptyListException {
        Query query = em.createQuery("SELECT d FROM Dispute d");
        
        List<Dispute> disputes = query.getResultList();
        
        if (disputes.isEmpty()) {
            throw new EmptyListException("List of listings is empty.\n");
        }
       
        
        return disputes;
    }
    @Override
    public void resolveDispute(Long disputeId) throws EntityNotFoundException {
        System.out.println("Resolve dispute.");
        Dispute updatedDispute = this.getDisputeByDisputeId(disputeId);
        
        updatedDispute.setIsResolved(true);
    }
    
    @Override
    public Dispute getDisputeByDisputeId(Long disputeId) throws EntityNotFoundException {
        if(disputeId != null){
            Dispute dispute = em.find(Dispute.class, disputeId);
            return dispute;
        } else {
            throw new EntityNotFoundException("Dispute ID " + disputeId + " does not exist!");
        }
        
    }
    
}
