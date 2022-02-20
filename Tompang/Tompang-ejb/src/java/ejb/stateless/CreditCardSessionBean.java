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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brend
 */
@Stateless
public class CreditCardSessionBean implements CreditCardSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCreditCard(CreditCard cc) {
        em.persist(cc);
        em.flush();
        
        return cc.getCcId();
    }
    
    @Override
    public List<CreditCard> retrieveAllCreditCards() throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM CreditCard c");
        
        List<CreditCard> creditCards = query.getResultList();
        
        if (creditCards.isEmpty()) throw new EmptyListException("List of credit cards is empty.\n");
        
        return creditCards;
    }
    
    @Override
    public CreditCard getCreditCardByCCId(Long ccId) throws EntityNotFoundException {
        CreditCard cc = em.find(CreditCard.class, ccId);
        
        if (cc == null) throw new EntityNotFoundException("CreditCard " + ccId + " not found.\n");
        
        return cc;
    }
    
    @Override
    public void deleteCreditCard(Long ccId) throws EntityNotFoundException {
        CreditCard cc = em.find(CreditCard.class, ccId);
        em.remove(cc);
    }
}
