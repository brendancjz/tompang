/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.singleton;

import entity.CreditCard;
import entity.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brend
 */
@Singleton
@Startup
public class DataInitSessionBean implements DataInitSessionBeanLocal {

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
            Date joinedOn = Date.from(LocalDate.of(2022, 2, 19).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User manager = new User("Brendan", "Chia", "bchia@gmail.com", "manager", "password", dob, 98769876L, joinedOn, true);

            em.persist(manager);
            em.flush();

            //Create Dummy User
            dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            joinedOn = Date.from(LocalDate.of(2022, 2, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User dummyUser = new User("Dummy", "User", "dummyUser@gmail.com", "dummy", "password", dob, 12341234L, joinedOn, false);

            em.persist(dummyUser);
            em.flush();
        }

        if (em.find(CreditCard.class, 1L) == null) {
            CreditCard cc = new CreditCard("BRENDAN CHIA", 4605123456781010L, 123, "05/25");
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User
            User manager = (User) em.find(User.class, 1L);
            manager.getCreditCards().add(cc);
        }
    }
}
