/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.singleton;

import ejb.stateless.PhotoSessionBeanLocal;
import entity.CreditCard;
import entity.Listing;
import entity.User;
import exception.EmptyListException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brend
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private PhotoSessionBeanLocal photoSessionBean;

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PostConstruct
    public void postConstruct() {
        System.out.println("=== DataInit postConstruct() called ====");

        if (em.find(User.class, 1L) == null) {
            //Create Manager
            Date dob = Date.from(LocalDate.of(1999, 12, 25).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //Date joinedOn = Date.from(LocalDate.of(2022, 2, 19).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User manager = new User("Brendan", "Chia", "bchia@gmail.com", "manager", "password", dob, 98769876L, true);

            em.persist(manager);
            em.flush();

            //Create Dummy User
            dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //joinedOn = Date.from(LocalDate.of(2022, 2, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User dummyUser = new User("Dummy", "User", "dummyUser@gmail.com", "dummy", "password", dob, 12341234L, false);
            em.persist(dummyUser);
            em.flush();
            
            //Create Dummy User2
            dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //joinedOn = Date.from(LocalDate.of(2022, 2, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User dummyUser2 = new User("Dummy2", "User2", "dummyUser2@gmail.com", "dummy2", "password", dob, 12341235L, false);
            em.persist(dummyUser2);
            em.flush();
        }

        if (em.find(CreditCard.class, 1L) == null) {
            Date expiryDate = Date.from(LocalDate.of(2025, 05, 01).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            CreditCard cc = new CreditCard("BRENDAN CHIA", 4605123456781010L, 123, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User
            User manager = (User) em.find(User.class, 1L);
            manager.getCreditCards().add(cc);
            
            expiryDate = Date.from(LocalDate.of(2025, 05, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DUMMY USER ONE", 4605123456781020L, 123, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User1
            User dummyUser1 = (User) em.find(User.class, 2L);
            dummyUser1.getCreditCards().add(cc);
            
            expiryDate = Date.from(LocalDate.of(2025, 05, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DUMMY USER TWO", 4605123456781030L, 123, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User2
            User dummyUser2 = (User) em.find(User.class, 3L);
            dummyUser2.getCreditCards().add(cc);
        }
        
        if (em.find(Listing.class, 1L) == null) {
            Date expectedArrivalDate = Date.from(LocalDate.now().atStartOfDay().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
            User dummyUser1 = (User) em.find(User.class, 2L);
            Listing listing = new Listing("Japan", "Osaka", "Japan Biscuit", "Lovely japanese biscuits!", 35.00, expectedArrivalDate, dummyUser1);
            em.persist(listing); 
            em.flush();
        }
        
        try {
            System.out.println(photoSessionBean.retrieveAllPhotos());
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
}
