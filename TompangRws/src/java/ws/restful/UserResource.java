/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.CreditCardSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.CreditCard;
import entity.Listing;
import entity.Transaction;
import entity.User;
import exception.CreateNewCreditCardException;
import exception.CreateNewUserException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.datamodel.CreateCreditCardReq;
import ws.datamodel.UpdateUserReq;

/**
 *
 * @author GuoJun
 */
@Path("User")
public class UserResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final UserSessionBeanLocal userSessionBean;

    private final CreditCardSessionBeanLocal creditCardSessionBean;

    public UserResource() {
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBean = lookupUserSessionBeanLocal();
        creditCardSessionBean = lookupCreditCardSessionBeanLocal();
    }

    @Path("retrieveUser/{userId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(@PathParam("userId") Long userId) {
        try {
            User user = userSessionBean.getUserByUserId(userId);

            // not supposed to be setting null, same problem as ListingResource

            if (user.getCreditCards() != null) {
                for (CreditCard creditCard : user.getCreditCards()) {
                    creditCard.setUser(null);
                }
            }

            if (user.getConversations() != null) {
                for (Conversation conversation : user.getConversations()) {
                    conversation.setListing(null);
                    conversation.setCreatedBy(null);
                    conversation.setMessages(null);
                }
            }

            if (user.getCreatedListings() != null) {
                for (Listing createdListing : user.getCreatedListings()) {
                    createdListing.setCreatedBy(null);
                    createdListing.setLikedByUsers(null);
                    createdListing.setTransactions(null);
                    createdListing.setConversations(null);
                }
            }

            if (user.getBuyerTransactions() != null) {
                for (Transaction buyerTransaction : user.getBuyerTransactions()) {
                    buyerTransaction.setListing(null);
                    buyerTransaction.setBuyer(null);
                    buyerTransaction.setSeller(null);
                    buyerTransaction.setDispute(null);
                    buyerTransaction.setCreditCard(null);
                }
            }

            if (user.getSellerTransactions() != null) {
                for (Transaction sellerTransaction : user.getSellerTransactions()) {
                    sellerTransaction.setListing(null);
                    sellerTransaction.setBuyer(null);
                    sellerTransaction.setSeller(null);
                    sellerTransaction.setDispute(null);
                    sellerTransaction.setCreditCard(null);
                }
            }

            if (user.getFollowers() != null) {
                for (User follower : user.getFollowers()) {
                    follower.setCreatedListings(null);
                    follower.setConversations(null);
                    follower.setCreditCards(null);
                    follower.setBuyerTransactions(null);
                    follower.setSellerTransactions(null);
                    follower.setFollowers(null);
                    follower.setFollowing(null);
                    follower.setLikedListings(null);
                }
            }

            if (user.getFollowing() != null) {
                for (User following : user.getFollowing()) {
                    following.setCreatedListings(null);
                    following.setConversations(null);
                    following.setCreditCards(null);
                    following.setBuyerTransactions(null);
                    following.setSellerTransactions(null);
                    following.getFollowers().clear();
                    following.getFollowing().clear();
                    following.setLikedListings(null);
                }
            }

            if (user.getLikedListings() != null) {
                for (Listing likedListing : user.getLikedListings()) {
                    likedListing.setCreatedBy(null);
                    likedListing.setLikedByUsers(null);
                    likedListing.setTransactions(null);
                    likedListing.setConversations(null);
                }
            }

            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();

        }
    }

    @Path("userLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");

            //user.setPassword(null);
            user.setSalt(null);
            
            if (user.getCreditCards() != null) {
                for (CreditCard creditCard : user.getCreditCards()) {
                    creditCard.setUser(null);
                }
            }

            if (user.getConversations() != null) {
                for (Conversation conversation : user.getConversations()) {
                    conversation.setListing(null);
                    conversation.setCreatedBy(null);
                    conversation.getMessages().clear();
                }
            }

            if (user.getCreatedListings() != null) {
                for (Listing createdListing : user.getCreatedListings()) {
                    createdListing.setCreatedBy(null);
                    createdListing.getLikedByUsers().clear();
                    createdListing.getTransactions().clear();
                    createdListing.getConversations().clear();
                }
            }

            if (user.getBuyerTransactions() != null) {
                for (Transaction buyerTransaction : user.getBuyerTransactions()) {
                    buyerTransaction.setListing(null);
                    buyerTransaction.setBuyer(null);
                    buyerTransaction.setSeller(null);
                    buyerTransaction.setDispute(null);
                    buyerTransaction.setCreditCard(null);
                }
            }

            if (user.getSellerTransactions() != null) {
                for (Transaction sellerTransaction : user.getSellerTransactions()) {
                    sellerTransaction.setListing(null);
                    sellerTransaction.setBuyer(null);
                    sellerTransaction.setSeller(null);
                    sellerTransaction.setDispute(null);
                    sellerTransaction.setCreditCard(null);
                }
            }

            if (user.getFollowers() != null) {
                for (User follower : user.getFollowers()) {
                    follower.getCreatedListings().clear();
                    follower.setConversations(null);
                    follower.setCreditCards(null);
                    follower.setBuyerTransactions(null);
                    follower.setSellerTransactions(null);
                    follower.getFollowers().clear();
                    follower.getFollowing().clear();
                    follower.setLikedListings(null);
                }
            }

            if (user.getFollowing() != null) {
                for (User following : user.getFollowing()) {
                    following.getCreatedListings().clear();
                    following.setConversations(null);
                    following.setCreditCards(null);
                    following.setBuyerTransactions(null);
                    following.setSellerTransactions(null);
                    following.getFollowers().clear();
                    following.getFollowing().clear();
                    following.setLikedListings(null);
                }
            }

            if (user.getLikedListings() != null) {
                for (Listing likedListing : user.getLikedListings()) {
                    likedListing.setCreatedBy(null);
                    likedListing.getLikedByUsers().clear();
                    likedListing.getTransactions().clear();
                    likedListing.getConversations().clear();
                }
            }
            
            

            return Response.status(Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch(Exception ex)
        {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (user != null) {
            try {

                Long userId = userSessionBean.createNewUser(user);
                return Response.status(Response.Status.OK).entity(userId).build();
            } catch (CreateNewUserException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update product request").build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserReq updateUserReq) {
        if (updateUserReq != null) {
            try {
                User user = userSessionBean.userLogin(updateUserReq.getUsername(), updateUserReq.getPassword());
                if(updateUserReq.getUser() != null){
                    
                    userSessionBean.updateUserDetails(updateUserReq.getUser());
                    System.out.println("Successful update of user details");
                } else {
                    userSessionBean.updateUserPassword(user.getUserId(), updateUserReq.getNewPassword());
                }

                return Response.status(Response.Status.OK).build();
            } catch (InvalidLoginCredentialsException ex) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            } catch (EntityNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update product request").build();
        }
    }

    @Path("retrieveUserCreditCards")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCreditCards(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");

            List<CreditCard> creditCards = user.getCreditCards();
            
            for(CreditCard cc: creditCards)
            {
                cc.setUser(null);
            }
            GenericEntity<List<CreditCard>> genericEntity = new GenericEntity<List<CreditCard>>(creditCards) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveCreditCard/{creditCardId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCreditCardByCCId(@QueryParam("username") String username,
            @QueryParam("password") String password, @PathParam("creditCardId") Long ccId) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");

            CreditCard creditCard = creditCardSessionBean.getCreditCardByCCId(ccId);
            
           
             creditCard.setUser(null);
            
           
            return Response.status(Status.OK).entity(creditCard).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (EntityNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } 
    }
    
    @Path("createCreditCard")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCreditCard(CreateCreditCardReq createCreditCardReq)
    {
       try {
     
            User user = userSessionBean.userLogin(createCreditCardReq.getUsername(), createCreditCardReq.getPassword());
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            Long ccId = creditCardSessionBean.createNewCreditCard(createCreditCardReq.getCreditCard(), user.getUserId());
            
            
            return Response.status(Response.Status.OK).entity(ccId).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (CreateNewCreditCardException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } 
     
   }
    
    @Path("creditCards/{ccId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCard(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("ccId") Long ccId) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");

            creditCardSessionBean.deleteCreditCard(ccId, user.getUserId());

            return Response.status(Response.Status.OK).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
    
    @Path("follow/{followingUserId}/{followedUserId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response follow(@QueryParam("username") String username,
            @QueryParam("password") String password, @PathParam("followingUserId") Long followingUserId, @PathParam("followedUserId") Long followedUserId ) {
        System.out.println("hi");
        try {
            userSessionBean.follow(followingUserId, followedUserId);
            return Response.status(Response.Status.OK).build(); 
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
    
    @Path("unfollow/{unfollowingUserId}/{unfollowedUserId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unfollow(@PathParam("unfollowingUserId") Long unfollowingUserId, @PathParam("unfollowedUserId") Long unfollowedUserId, @QueryParam("username") String username,
            @QueryParam("password") String password ) {
        
        try {
            userSessionBean.unfollow(unfollowingUserId, unfollowedUserId);
            return Response.status(Response.Status.OK).build(); 
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    private UserSessionBeanLocal lookupUserSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/UserSessionBean!ejb.stateless.UserSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CreditCardSessionBeanLocal lookupCreditCardSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CreditCardSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/CreditCardSessionBean!ejb.stateless.CreditCardSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
