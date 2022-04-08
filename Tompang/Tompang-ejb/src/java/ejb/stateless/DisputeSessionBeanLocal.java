/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Dispute;
import exception.CreateNewDisputeException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author seanang
 */
@Local
public interface DisputeSessionBeanLocal {
    
    public List<Dispute> retrieveAllDisputes() throws EmptyListException;
    
    public Long createNewDispute(Long transactionId, Dispute dispute) throws CreateNewDisputeException;
    
    public void resolveDispute(Long disputeId) throws EntityNotFoundException;
    
    public Dispute getDisputeByDisputeId(Long disputeId) throws EntityNotFoundException;

    public List<Dispute> retrieveUserDisputes(Long userId);

   
}
