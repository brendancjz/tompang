/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.singleton;

import ejb.stateless.CreditCardSessionBeanLocal;
import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.CreditCard;
import entity.Dispute;
import entity.Listing;
import entity.Message;
import entity.Transaction;
import entity.User;
import exception.CreateNewCreditCardException;
import exception.CreateNewListingException;
import exception.CreateNewUserException;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private CreditCardSessionBeanLocal creditCardSessionBean;

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

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

            try {
                initialiseAllUsers();
                initialiseCreditCardsToUsers();

                initialiseAllListings();
            } catch (CreateNewUserException | CreateNewCreditCardException | CreateNewListingException ex) {
                System.out.print(ex.getMessage());
            }
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
            convo.setSeller(japanBiscuit.getCreatedBy());
            em.persist(convo);
            em.flush();
        }

        if (em.find(Message.class, 1L) == null) {
            String buyerToSeller = "Hi, can i buy this?";
            String sellerToBuyer = "Sure!";
            User buyerAdmin = (User) em.find(User.class, 2L);
            User sellerManager = (User) em.find(User.class, 1L);
            Message initiate = new Message(buyerToSeller, true, buyerAdmin.getUserId(), false);
            Message response = new Message(sellerToBuyer, false, sellerManager.getUserId(), false);
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

    private void initialiseAllUsers() throws CreateNewUserException {
        Date dob = Date.from(LocalDate.of(1999, 12, 25).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String managerProfilePic = "/uploadedFiles/manager_picture.jpg";
        User manager = new User("Brendan", "Chia", "bchia@gmail.com", "manager", "password", dob, managerProfilePic, 98769876L, true);
        userSessionBean.createNewUser(manager);

        String userProfilePic = "/uploadedFiles/default_picture.jpg";
        //Create Admin
        dob = Date.from(LocalDate.of(1999, 12, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        User admin = new User("Sean", "Ang", "sean.ang@gmail.com", "admin", "password", dob, userProfilePic, 98769871L, true);
        userSessionBean.createNewUser(admin);

        //Create User3
        dob = Date.from(LocalDate.of(1999, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        User iggy = new User("Ignitius", "Goh", "ig.goh@gmail.com", "iggy", "password", dob, userProfilePic, 12341234L, false);
        userSessionBean.createNewUser(iggy);

        //Create User4
        dob = Date.from(LocalDate.of(1999, 2, 2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        User alice = new User("Alice", "Tan", "alice.tan@gmail.com", "alice", "password", dob, userProfilePic, 12341235L, false);
        userSessionBean.createNewUser(alice);

        //Create User5 for following
        String guojunPic = "/uploadedFiles/guojun.jpg";
        dob = Date.from(LocalDate.of(1999, 12, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        User guojun = new User("Guo Jun", "Heng", "gwajoon@gmail.com", "guojun", "password", dob, guojunPic, 98769872L, true);
        userSessionBean.createNewUser(guojun);

        //Create User6
        dob = Date.from(LocalDate.of(1999, 3, 3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        User user = new User("Keng Yong", "Tham", "keng.yong@gmail.com", "kengyong", "password", dob, userProfilePic, 84821212L, false);
        userSessionBean.createNewUser(user);
        //Create User7
        dob = Date.from(LocalDate.of(1999, 4, 4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Ryan", "Ng", "ryan.ng@gmail.com", "ryanng", "password", dob, userProfilePic, 84821313L, false);
        userSessionBean.createNewUser(user);
        //Create User8
        dob = Date.from(LocalDate.of(1999, 5, 5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Robert", "Hartog", "robert.hartog@gmail.com", "roberthartog", "password", dob, userProfilePic, 84821414L, false);
        userSessionBean.createNewUser(user);
        //Create User9
        dob = Date.from(LocalDate.of(1999, 6, 6).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Cedric", "Tan", "cedric.tan@gmail.com", "decridtan", "password", dob, userProfilePic, 84821515L, false);
        userSessionBean.createNewUser(user);
        //Create User10
        dob = Date.from(LocalDate.of(1999, 7, 7).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Jack", "Sng", "jack.sng@gmail.com", "jacksng", "password", dob, userProfilePic, 84821616L, false);
        userSessionBean.createNewUser(user);
        //Create User11
        dob = Date.from(LocalDate.of(1999, 8, 8).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Samuel", "Cheang", "samuel.cheang@gmail.com", "samuelcheang", "password", dob, userProfilePic, 84821717L, false);
        userSessionBean.createNewUser(user);
        //Create User12
        dob = Date.from(LocalDate.of(1999, 9, 9).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Robin", "Sow", "roobin.soh@gmail.com", "robinsoh", "password", dob, userProfilePic, 84821818L, false);
        userSessionBean.createNewUser(user);
        //Create User13
        dob = Date.from(LocalDate.of(1999, 10, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Anna", "Lim", "anna.lim@gmail.com", "annalim", "password", dob, userProfilePic, 84821919L, false);
        userSessionBean.createNewUser(user);
        //Create User14
        dob = Date.from(LocalDate.of(1999, 11, 11).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Sarah", "Goh", "sarah.goh@gmail.com", "sarahgoh", "password", dob, userProfilePic, 84822020L, false);
        userSessionBean.createNewUser(user);
        //Create User15
        dob = Date.from(LocalDate.of(1999, 12, 12).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        user = new User("Gwen", "Doo", "gwen.doo@gmail.com", "gwendoo", "password", dob, userProfilePic, 84822121L, false);
        userSessionBean.createNewUser(user);

        user = (User) em.find(User.class, 5L);
        // manager has 5 follower :guojun
        // iggy has 2 followers : guojun and manager
        // guojun has 1 follower: manager

        try {

            //Users following manager
            userSessionBean.follow(guojun.getUserId(), manager.getUserId());
            userSessionBean.follow(6L, manager.getUserId());
            userSessionBean.follow(7L, manager.getUserId());
            userSessionBean.follow(8L, manager.getUserId());
            userSessionBean.follow(9L, manager.getUserId());

            //Users following admin
            userSessionBean.follow(6L, 2L);
            userSessionBean.follow(1L, 2L);
            userSessionBean.follow(12L, 2L);

            //Users following iggy
            userSessionBean.follow(manager.getUserId(), iggy.getUserId());
            userSessionBean.follow(guojun.getUserId(), iggy.getUserId());
            userSessionBean.follow(4L, iggy.getUserId());
            userSessionBean.follow(10L, iggy.getUserId());
            
            //Users following Alice
            userSessionBean.follow(1L, 4L);
            userSessionBean.follow(13L, 4L);
            userSessionBean.follow(7L, 4L);
            
            //Users following GuoJun
            userSessionBean.follow(6L, 5L);
            userSessionBean.follow(7L, 5L);
            userSessionBean.follow(8L, 5L);
            userSessionBean.follow(9L, 5L);
            userSessionBean.follow(10L, 5L);
            userSessionBean.follow(11L, 5L);
            
            //Users following keng yong
            userSessionBean.follow(2L, 6L);
            userSessionBean.follow(3L, 6L);
            userSessionBean.follow(4L, 6L);
            userSessionBean.follow(5L, 6L);
            
            //Users following ryan ng
            userSessionBean.follow(1L, 7L);
            userSessionBean.follow(4L, 7L);
            
            //Users following robert
            userSessionBean.follow(11L, 8L);
            userSessionBean.follow(12L, 8L);
            userSessionBean.follow(13L, 8L);
            userSessionBean.follow(14L, 8L);
            userSessionBean.follow(15L, 8L);
            
            //Users following cedric
            userSessionBean.follow(12L, 9L);
            userSessionBean.follow(13L, 9L);
            userSessionBean.follow(15L, 9L);
            
            //Users following jack sng
            userSessionBean.follow(1L, 10L);
            userSessionBean.follow(3L, 10L);
            userSessionBean.follow(2L, 10L);
            userSessionBean.follow(13L, 10L);
            
            //Users following samuel
            userSessionBean.follow(1L, 11L);
            userSessionBean.follow(3L, 11L);
            
            //Users following robin
            userSessionBean.follow(2L, 12L);
            userSessionBean.follow(3L, 12L);
            userSessionBean.follow(4L, 12L);
            userSessionBean.follow(5L, 12L);
            
            //Users following anna
            userSessionBean.follow(1L, 13L);
            userSessionBean.follow(3L, 13L);
            userSessionBean.follow(4L, 13L);
            userSessionBean.follow(6L, 13L);
            userSessionBean.follow(8L, 13L);
            userSessionBean.follow(11L, 13L);
            
            //Users following sarah
            userSessionBean.follow(15L, 14L); 
            userSessionBean.follow(11L, 14L);
            
        } catch (EntityNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void initialiseCreditCardsToUsers() throws CreateNewCreditCardException {
        if (em.find(CreditCard.class, 1L) == null) {
            Date expiryDate = Date.from(LocalDate.of(2025, 05, 01).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            CreditCard cc = new CreditCard("DBS", "BRENDAN CHIA", 4605123456781010L, 123, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 1L);
            expiryDate = Date.from(LocalDate.of(2026, 07, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("SC", "CHIA JUN ZHE", 1234987645672345L, 321, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 1L);

            expiryDate = Date.from(LocalDate.of(2025, 05, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("AMEX", "SEAN ANG", 4605123456781020L, 123, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 2L);

            expiryDate = Date.from(LocalDate.of(2025, 05, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("SC", "IGNITIUS GOH", 4605123456781030L, 123, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 3L);

            expiryDate = Date.from(LocalDate.of(2024, 03, 12).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("OCBC", "ALICE TAN", 4327918256781133L, 125, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 4L);

            expiryDate = Date.from(LocalDate.of(2025, 07, 22).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DBS", "GUO JUN HENG", 4605123456781133L, 125, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 5L);

            expiryDate = Date.from(LocalDate.of(2024, 06, 22).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("UOB", "KENG YONG", 4605123123781133L, 124, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 6L);

            expiryDate = Date.from(LocalDate.of(2024, 01, 01).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("UOB", "RYAN NG WANG DONG", 4605123143565733L, 544, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 7L);

            expiryDate = Date.from(LocalDate.of(2026, 04, 14).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("UOB", "ROBERT HARTOG", 5467453256789876L, 933, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 8L);

            expiryDate = Date.from(LocalDate.of(2023, 02, 24).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DBS", "CEDRIC TAN", 4605142256789876L, 535, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 9L);

            expiryDate = Date.from(LocalDate.of(2026, 06, 14).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DBS", "SNG JACK", 5467453256111111L, 213, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 10L);

            expiryDate = Date.from(LocalDate.of(2024, 11, 14).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("SC", "SAMUEL CHEANG", 5227453256111132L, 177, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 11L);

            expiryDate = Date.from(LocalDate.of(2025, 11, 05).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("AMEX", "ROBIN SOH", 3456453256111132L, 127, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 12L);

            expiryDate = Date.from(LocalDate.of(2025, 05, 12).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("OCBC", "ANNA LIM", 7890453256111132L, 227, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 13L);

            expiryDate = Date.from(LocalDate.of(2025, 04, 22).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("AMEX", "SARAH GOH", 7890453256118791L, 267, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 14L);

            expiryDate = Date.from(LocalDate.of(2023, 02, 02).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            cc = new CreditCard("DBS", "GWENDOLINE DOO", 4723453256118791L, 879, expiryDate);
            this.creditCardSessionBean.createNewCreditCard(cc, 15L);
        }
    }

    private void initialiseAllListings() throws CreateNewListingException {
        if (em.find(Listing.class, 1L) == null) {
            Date expectedArrivalDate = Date.from(LocalDate.now().atStartOfDay().plusDays(3).atZone(ZoneId.systemDefault()).toInstant());
            Date expectedArrivalDate2 = Date.from(LocalDate.now().atStartOfDay().plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
            Date expectedArrivalDate3 = Date.from(LocalDate.now().atStartOfDay().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
            Date expectedArrivalDate4 = Date.from(LocalDate.now().atStartOfDay().plusDays(9).atZone(ZoneId.systemDefault()).toInstant());
            Date expectedArrivalDate5 = Date.from(LocalDate.now().atStartOfDay().plusDays(12).atZone(ZoneId.systemDefault()).toInstant());

            User manager = (User) em.find(User.class, 1L);
            List<String> photos1 = new ArrayList<>();
            photos1.add("/uploadedFiles/japanese_biscuits.jpeg");
            Listing listing = new Listing("Japan", "Osaka", "Japan Biscuit", "Lovely japanese biscuits!", "FOOD", 35.00, expectedArrivalDate, manager, 5, photos1);
            this.listingSessionBean.createNewListing(listing, 1L);

            List<String> photosKeyboard = new ArrayList<>();
            photosKeyboard.add("/uploadedFiles/keyboard_1.jpg");
            photosKeyboard.add("/uploadedFiles/keyboard_2.jpg");
            photosKeyboard.add("/uploadedFiles/keyboard_3.jpg");
            listing = new Listing("USA", "Washington", "Mechanical Keyboard Pokemon Style", "Work on a minimalist keyboard that places every key, command, and shortcut at your fingertips.\n"
                    + "The minimalist form factor aligns your shoulders and allows you to place your mouse closer to your keyboard for less hand reaching.",
                    "ELECTRONICS", 65.00, expectedArrivalDate2, manager, 3, photosKeyboard);
            this.listingSessionBean.createNewListing(listing, 1L);

            List<String> photosHeadphone = new ArrayList<>();
            photosHeadphone.add("/uploadedFiles/headphone_1.jpg");
            photosHeadphone.add("/uploadedFiles/headphone_2.jpg"); 
            photosHeadphone.add("/uploadedFiles/headphone_3.jpg");
            photosHeadphone.add("/uploadedFiles/headphone_4.jpg");

            listing = new Listing("USA", "Michigan", "Noise Cancelling Wireless Bluetooth Headphones", "I'm heading over to Michigan for business. Office is near the official Bose store. Reach out if you would like to buy a pair!",
                    "ELECTRONICS", 600.00, expectedArrivalDate3, manager, 2, photosHeadphone);
            this.listingSessionBean.createNewListing(listing, 1L);

            List<String> photosChampionTee = new ArrayList<>();
            photosChampionTee.add("/uploadedFiles/champion_tee_1.jpg");
            photosChampionTee.add("/uploadedFiles/champion_tee_2.jpg");
            listing = new Listing("Korea", "Tokyo", "Soft Champion T-Shirt", "I'm heading over to Michigan for business. Office is near the official Champion store. Reach out if you would like to buy a piece or two!",
                    "APPAREL", 45.00, expectedArrivalDate5, manager, 5, photosChampionTee);
            this.listingSessionBean.createNewListing(listing, 1L);

            List<String> photosChampionShorts = new ArrayList<>();
            photosChampionShorts.add("/uploadedFiles/champion_shorts_1.jpg");
            photosChampionShorts.add("/uploadedFiles/champion_shorts_2.jpg");
            photosChampionShorts.add("/uploadedFiles/champion_shorts_3.jpg");
            listing = new Listing("Korea", "Tokyo", "Soft Champion Shorts", "I'm heading over to Michigan for business. Office is near the official Champion store. Reach out if you would like to buy a pair or two!",
                    "APPAREL", 35.00, expectedArrivalDate5, manager, 4, photosChampionShorts);
            this.listingSessionBean.createNewListing(listing, 1L);

            User admin = (User) em.find(User.class, 2L);
            List<String> photos2 = new ArrayList<>();
            photos2.add("/uploadedFiles/tteokbokki.jpg");
            listing = new Listing("Korea", "Seoul", "Tteokbokki", "Authentic spicy rice cakes!", "FOOD", 15.00, expectedArrivalDate, admin, 5, photos2);
            this.listingSessionBean.createNewListing(listing, 2L);

            List<String> photosShoes = new ArrayList<>();
            photosShoes.add("/uploadedFiles/adidas_shoes_1.jpg");
            photosShoes.add("/uploadedFiles/adidas_shoes_2.jpg");
            photosShoes.add("/uploadedFiles/adidas_shoes_3.jpg");
            listing = new Listing("Malaysia", "George Town", "Adidas Alphatorsion 2.0 - Women Running Shoes", "VERSATILE RUNNING SHOES THAT SUPPORT ALL YOUR WORKOUT GOALS.\n"
                    + " Diversify your training and get a leg up on the competition. Box jumps, bear crawls, burpees? Take it all in stride and push towards excellence in these adidas running shoes.",
                    "FOOTWEAR", 150.00, expectedArrivalDate2, admin, 2, photosShoes);
            this.listingSessionBean.createNewListing(listing, 2L);

            User iggy = (User) em.find(User.class, 3L);
            List<String> photos3 = new ArrayList<>();
            photos3.add("/uploadedFiles/gummy.jpg");
            listing = new Listing("Japan", "Chiba", "Gummy Candy", "Gluten Tag!", "FOOD", 7.00, expectedArrivalDate3, iggy, 10, photos3);
            this.listingSessionBean.createNewListing(listing, 3L);

            List<String> photosSunblock = new ArrayList<>();
            photosSunblock.add("/uploadedFiles/sunblock_1.jpg");
            photosSunblock.add("/uploadedFiles/sunblock_2.jpg");
            listing = new Listing("Japan", "Kawasaki", "Anessa UV Sunscreen Skin Care", "Sunscreen skin care 60ml authentic from Japan. SPF50+ moisturiser", "GIFTS", 25.00, expectedArrivalDate4, iggy, 8, photosSunblock);
            this.listingSessionBean.createNewListing(listing, 3L);

            List<String> photosBagpack = new ArrayList<>();
            photosBagpack.add("/uploadedFiles/bagpack_1.jpg");
            photosBagpack.add("/uploadedFiles/bagpack_2.jpg");
            listing = new Listing("USA", "Colorado", "Wandrd Prvke Bagpack", "Two options, 21L or 31L. Let me know ASAP and I can only buy back 2 due to limited luggage space.", "GIFTS", 250.00, expectedArrivalDate5, iggy, 2, photosBagpack);
            this.listingSessionBean.createNewListing(listing, 3L);

            List<String> photosChain = new ArrayList<>();
            photosChain.add("/uploadedFiles/chain_1.jpg");
            listing = new Listing("Korea", "Busan", "Stainless Steel Bracelet", "There are these cool bracelet nearby my area in Korea right now. Anybody wants one?", "ACCESSORIES", 25.00, expectedArrivalDate5, iggy, 12, photosChain);
            this.listingSessionBean.createNewListing(listing, 4L);

            User guojun = (User) em.find(User.class, 5L);
            List<String> photos = new ArrayList<>();
            photos.add("/uploadedFiles/bathing_ape_bape.jpg");
            photos.add("/uploadedFiles/bathing_ape_bape_sizing_chart.jpg");
            listing = new Listing("Korea", "Seoul", "Black Bape T-Shirt", "Going to Korea for a business trip. Will pass by their local Bathing Ape store. Let's chat if you're keen to buy!", "APPAREL", 100.00, expectedArrivalDate3, guojun, 3, photos);
            this.listingSessionBean.createNewListing(listing, 5L);

            try {
                // LIKE LISTINGS
                //manager liking some listings
                listingSessionBean.incrementListingLikes(4L);
                userSessionBean.associateListingToUserLikedListings(1L, 6L);
                listingSessionBean.incrementListingLikes(9L);
                userSessionBean.associateListingToUserLikedListings(1L, 9L);
                listingSessionBean.incrementListingLikes(3L);
                userSessionBean.associateListingToUserLikedListings(1L, 3L);
                //admin liking some listings
                listingSessionBean.incrementListingLikes(4L);
                userSessionBean.associateListingToUserLikedListings(2L, 4L);
                listingSessionBean.incrementListingLikes(5L);
                userSessionBean.associateListingToUserLikedListings(2L, 5L);
                listingSessionBean.incrementListingLikes(10L);
                userSessionBean.associateListingToUserLikedListings(2L, 10L);
                //iggy liking some listings
                listingSessionBean.incrementListingLikes(4L);
                userSessionBean.associateListingToUserLikedListings(3L, 4L);
                listingSessionBean.incrementListingLikes(5L);
                userSessionBean.associateListingToUserLikedListings(3L, 5L);
                listingSessionBean.incrementListingLikes(10L);
                userSessionBean.associateListingToUserLikedListings(3L, 10L);
                listingSessionBean.incrementListingLikes(1L);
                userSessionBean.associateListingToUserLikedListings(3L, 1L);
                listingSessionBean.incrementListingLikes(2L);
                userSessionBean.associateListingToUserLikedListings(3L, 2L);
                listingSessionBean.incrementListingLikes(3L);
                userSessionBean.associateListingToUserLikedListings(3L, 3L);
                //guojun liking some listings
                listingSessionBean.incrementListingLikes(6L);
                userSessionBean.associateListingToUserLikedListings(5L, 6L);
                listingSessionBean.incrementListingLikes(8L);
                userSessionBean.associateListingToUserLikedListings(5L, 8L);
            } catch (EntityNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
