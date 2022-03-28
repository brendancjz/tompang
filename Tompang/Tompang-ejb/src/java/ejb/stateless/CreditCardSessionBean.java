/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.CreditCard;
import entity.User;
import exception.CreateNewCreditCardException;
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
 * @author brend
 */
@Stateless
public class CreditCardSessionBean implements CreditCardSessionBeanLocal {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCreditCard(CreditCard cc, Long userId) throws CreateNewCreditCardException {
        try {
            User user = userSessionBeanLocal.getUserByUserId(userId);
            if (!user.getCreditCards().contains(cc)) {
                user.getCreditCards().add(cc);
            }
        } catch (EntityNotFoundException ex) {
            throw new CreateNewCreditCardException();
        }
        em.persist(cc);
        em.flush();
        return cc.getCcId();
    }

    @Override
    public List<CreditCard> retrieveAllCreditCards() throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM CreditCard c");

        List<CreditCard> creditCards = query.getResultList();

        if (creditCards.isEmpty()) {
            throw new EmptyListException("List of credit cards is empty.\n");
        }

        return creditCards;
    }

    @Override
    public CreditCard getCreditCardByCCId(Long ccId) throws EntityNotFoundException {
        CreditCard cc = em.find(CreditCard.class, ccId);

        if (cc == null) {
            throw new EntityNotFoundException("CreditCard " + ccId + " not found.\n");
        }

        return cc;
    }
    
     @Override
    public CreditCard getCreditCardByCCNumber(Long ccNumber) throws EntityNotFoundException {
        Query query = em.createQuery("SELECT c FROM CreditCard c WHERE c.ccNumber = ?1");
                
        query.setParameter(1, ccNumber);
        CreditCard cc = (CreditCard)query.getSingleResult();
        
        if (cc == null) {
            throw new EntityNotFoundException("CreditCard " + ccNumber + " not found.\n");
        }

        return cc;
    }

    @Override
    public void deleteCreditCard(Long ccId, Long userId) throws EntityNotFoundException {
        CreditCard cc = em.find(CreditCard.class, ccId);
        User user = userSessionBeanLocal.getUserByUserId(userId);
        if (user.getCreditCards().contains(cc)) {
            user.getCreditCards().remove(cc);
        }
        em.remove(cc);
    }
}
