/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.singleton;

import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.CreditCard;
import entity.Dispute;
import entity.Listing;
import entity.Message;
import entity.Transaction;
import entity.User;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private UserSessionBeanLocal userSessionBean;

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
            String managerProfilePic = "/uploadedFiles/manager_picture.jpg";
            User manager = new User("Brendan", "Chia", "bchia@gmail.com", "manager", "password", dob, managerProfilePic, 98769876L, true);

            em.persist(manager);
            em.flush();

            String userProfilePic = "/uploadedFiles/default_picture.jpg";

            //Create Admin
            dob = Date.from(LocalDate.of(1999, 12, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            User admin = new User("Sean", "Ang", "sean.ang@gmail.com", "admin", "password", dob, userProfilePic, 98769871L, true);

            em.persist(admin);
            em.flush();

            //Create User1
            dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User iggy = new User("Ignitius", "Goh", "ig.goh@gmail.com", "iggy", "password", dob, userProfilePic, 12341234L, false);
            em.persist(iggy);
            em.flush();

            //Create User2
            dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User alice = new User("Alice", "Tan", "alice.tan@gmail.com", "alice", "password", dob, userProfilePic, 12341235L, false);
            em.persist(alice);
            em.flush();

            //Create User3 for following
            String guojunPic = "/uploadedFiles/guojun.jpg";
            dob = Date.from(LocalDate.of(1999, 12, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User guojun = new User("Guo Jun", "Heng", "gwajoon@gmail.com", "guojun", "password", dob, guojunPic, 98769872L, true);
            em.persist(guojun);
            em.flush();

            User user = (User) em.find(User.class, 5L);
            // manager has 1 follower :guojun
            // iggy has 2 followers : guojun and manager
            // guojun has 1 follower: manager
            try {
                userSessionBean.follow(manager.getUserId(), iggy.getUserId());
                userSessionBean.follow(guojun.getUserId(), iggy.getUserId());
                userSessionBean.follow(guojun.getUserId(), manager.getUserId());
            } catch (EntityNotFoundException ex) {
                System.out.print(ex.getMessage());
            }
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
            cc = new CreditCard("SEAN ANG", 4605123456781020L, 123, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User1
            User admin = (User) em.find(User.class, 2L);
            admin.getCreditCards().add(cc);

            expiryDate = Date.from(LocalDate.of(2025, 05, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("Ignitius Goh", 4605123456781030L, 123, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User2
            User iggy = (User) em.find(User.class, 3L);
            iggy.getCreditCards().add(cc);
            
            expiryDate = Date.from(LocalDate.of(2025, 07, 22).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("Guo Jun Heng", 4605123456781133L, 125, expiryDate);
            em.persist(cc);
            em.flush();

            //Associate CreditCard to User2
            User guojun = (User) em.find(User.class, 5L);
            guojun.getCreditCards().add(cc);
        }

        if (em.find(Listing.class, 1L) == null) {
            Date expectedArrivalDate = Date.from(LocalDate.now().atStartOfDay().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
            User manager = (User) em.find(User.class, 1L);
            List<String> photos1 = new ArrayList<>();
            photos1.add("/uploadedFiles/japanese_biscuits.jpeg");
            Listing listing = new Listing("Japan", "Osaka", "Japan Biscuit", "Lovely japanese biscuits!", "FOOD", 35.00, expectedArrivalDate, manager, 5, photos1);
            em.persist(listing);
            em.flush();
            
            List<String> photosKeyboard = new ArrayList<>();
            photosKeyboard.add("/uploadedFiles/keyboard_1.jpg");
            photosKeyboard.add("/uploadedFiles/keyboard_2.jpg");
            photosKeyboard.add("/uploadedFiles/keyboard_3.jpg");
            listing = new Listing("USA", "Washington", "Mechanical Keyboard Pokemon Style", "Work on a minimalist keyboard that places every key, command, and shortcut at your fingertips.\n" +
                    "The minimalist form factor aligns your shoulders and allows you to place your mouse closer to your keyboard for less hand reaching.", 
                    "ELECTRONICS", 65.00, expectedArrivalDate, manager, 3, photosKeyboard);
            em.persist(listing);
            em.flush();

            List<String> photosHeadphone = new ArrayList<>();
            photosHeadphone.add("/uploadedFiles/headphone_1.jpg");
            photosHeadphone.add("/uploadedFiles/headphone_2.jpg");
            photosHeadphone.add("/uploadedFiles/headphone_3.jpg");
            photosHeadphone.add("/uploadedFiles/headphone_4.jpg");
            
            listing = new Listing("USA", "Michigan", "Noise Cancelling Wireless Bluetooth Headphones", "I'm heading over to Michigan for business. Office is near the official Bose store. Reach out if you would like to buy a pair!", 
                    "ELECTRONICS", 600.00, expectedArrivalDate, manager, 2, photosKeyboard);
            em.persist(listing);
            em.flush();
            
            User admin = (User) em.find(User.class, 2L);
            List<String> photos2 = new ArrayList<>();
            photos2.add("/uploadedFiles/tteokbokki.jpg");
            listing = new Listing("Korea", "Seoul", "Tteokbokki", "Authentic spicy rice cakes!", "FOOD", 15.00, expectedArrivalDate, admin, 5, photos2);
            em.persist(listing);
            em.flush();
            
            List<String> photosShoes = new ArrayList<>();
            photosShoes.add("/uploadedFiles/adidas_shoes_1.jpg");
            photosShoes.add("/uploadedFiles/adidas_shoes_2.jpg");
            photosShoes.add("/uploadedFiles/adidas_shoes_3.jpg");
            listing = new Listing("Malaysia", "George Town", "Adidas Alphatorsion 2.0 - Women Running Shoes", "VERSATILE RUNNING SHOES THAT SUPPORT ALL YOUR WORKOUT GOALS.\n" +
" Diversify your training and get a leg up on the competition. Box jumps, bear crawls, burpees? Take it all in stride and push towards excellence in these adidas running shoes.", 
                    "FOOTWEAR", 150.00, expectedArrivalDate, admin, 2, photosShoes);
            em.persist(listing);
            em.flush();

            User iggy = (User) em.find(User.class, 3L);
            List<String> photos3 = new ArrayList<>();
            photos3.add("/uploadedFiles/gummy.jpg");
            listing = new Listing("Japan", "Chiba", "Gummy Candy", "Gluten Tag!", "FOOD", 7.00, expectedArrivalDate, iggy, 10, photos3);
            em.persist(listing);
            em.flush();

            User guojun = (User) em.find(User.class, 5L);
            List<String> photos = new ArrayList<>();
            photos.add("/uploadedFiles/bathing_ape_bape.jpg");
            photos.add("/uploadedFiles/bathing_ape_bape_sizing_chart.jpg");
            listing = new Listing("Korea", "Seoul", "Black Bape T-Shirt", "Going to Korea for a business trip. Will pass by their local Bathing Ape store. Let's chat if you're keen to buy!", "APPAREL", 100.00, expectedArrivalDate, guojun, 3, photos);
            em.persist(listing);
            em.flush();
        }

        if (em.find(Transaction.class, 1L) == null) {
            Date createdOn = Date.from(LocalDate.of(2022, 3, 12).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            User dummyUser2 = (User) em.find(User.class, 2L);
            User dummyUser3 = (User) em.find(User.class, 3L);
            Listing listing3 = (Listing) em.find(Listing.class, 3L);
            Transaction transaction1 = new Transaction(Double.parseDouble("100"), createdOn, dummyUser2, dummyUser3, listing3, dummyUser2.getCreditCards().get(0));
            em.persist(transaction1);
            em.flush();
            
            //same listing 3
            User guojun = (User) em.find(User.class, 5L);
            Transaction transaction2 = new Transaction(Double.parseDouble("100"), createdOn, guojun, dummyUser3, listing3, guojun.getCreditCards().get(0));
            transaction2.setIsCompleted(true);
            transaction2.setHasDispute(true);
            em.persist(transaction2);
            em.flush();
            
            Dispute dispute = new Dispute("Seller does not want to buy additional product for me.", transaction2);
            em.persist(dispute);
            em.flush();
            transaction2.setDispute(dispute);
        }

        if (em.find(Conversation.class, 1L) == null) {
            // japan biscuit created by manager (seller) convo initaited by admin (buyer)
            Listing japanBiscuit = (Listing) em.find(Listing.class, 1L);
            User buyerAdmin = (User) em.find(User.class, 2L);
            Conversation convo = new Conversation(buyerAdmin, japanBiscuit);
            em.persist(convo);
            em.flush();
        }

        if (em.find(Message.class, 1L) == null) {
            String buyerToSeller = "Hi, can i buy this?";
            String sellerToBuyer = "Sure!";
            User buyerAdmin = (User) em.find(User.class, 2L);
            User sellerManager = (User) em.find(User.class, 1L);
            Message initiate = new Message(buyerToSeller, true, buyerAdmin.getUserId());
            Message response = new Message(sellerToBuyer, false, sellerManager.getUserId());
            em.persist(initiate);
            em.persist(response);
            em.flush();
            Conversation convo = em.find(Conversation.class, 1L);
            convo.getMessages().add(initiate);
            convo.getMessages().add(response);
        }

       
        try {

            System.out.println(userSessionBean.retrieveAllUsers());

            User user = userSessionBean.getUserByUserId(1L);
            //userSessionBean.updateUserDetails(user.getUserId(), user.getFirstName(), "User", user.getEmail(), user.getUsername(), user.getDateOfBirth(), user.getContactNumber());

            for (Listing listing : user.getLikedListings()) {
                System.out.println(listing.getListingId());
            }
            System.out.println();
        } catch (EntityNotFoundException | EmptyListException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
