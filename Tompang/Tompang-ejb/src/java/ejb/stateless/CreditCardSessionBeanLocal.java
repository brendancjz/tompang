/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.CreditCard;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface CreditCardSessionBeanLocal {

    public Long createNewCreditCard(CreditCard cc);

    public List<CreditCard> retrieveAllCreditCards() throws EmptyListException;

    public CreditCard getCreditCardByCCId(Long ccId) throws EntityNotFoundException;

    public void deleteCreditCard(Long ccId) throws EntityNotFoundException;
    
}
