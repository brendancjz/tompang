/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.TransactionSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Dispute;
import entity.Transaction;
import entity.User;
import exception.CreateNewDisputeException;
import exception.InvalidLoginCredentialsException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateDisputeReq;
import ejb.stateless.DisputeSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author Sean Ang
 */
@Path("Dispute")
public class DisputeResource {

    

    
    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final UserSessionBeanLocal userSessionBean;
    private final TransactionSessionBeanLocal transactionSessionBean;
    private final DisputeSessionBeanLocal disputeSessionBean;
    

    public DisputeResource() {
        sessionBeanLookup = new SessionBeanLookup();
        transactionSessionBean = lookupTransactionSessionBeanLocal();
        userSessionBean = lookupUserSessionBeanLocal();
        disputeSessionBean = lookupDisputeSessionBeanLocal();
    }

    

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDispute(CreateDisputeReq createDisputeReq) {
        if (createDisputeReq != null) {
            try {
                User user = userSessionBean.userLogin(createDisputeReq.getUsername(), createDisputeReq.getPassword());
                
                Long disputeId = disputeSessionBean.createNewDispute(createDisputeReq.getTransactionId(), createDisputeReq.getDispute(), user.getUserId());

                return Response.status(Response.Status.OK).entity(disputeId).build();
            } catch (InvalidLoginCredentialsException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            } catch (CreateNewDisputeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
            
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new listing request").build();
        }
    }
    
    @Path("retrieveDispute/{disputeId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveDisputeById(@QueryParam("username") String username, 
                                    @QueryParam("password") String password,
                                    @PathParam("disputeId") Long disputeId){
    
         try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
           
            Dispute dispute = disputeSessionBean.getDisputeByDisputeId(disputeId);
            Transaction transaction = dispute.getTransaction();
            if (transaction.getBuyer() != null) {
                 transaction.getBuyer().getCreatedListings().clear();
                 transaction.getBuyer().setConversations(null);
                 transaction.getBuyer().setCreditCards(null);
                 transaction.getBuyer().setBuyerTransactions(null);
                 transaction.getBuyer().setSellerTransactions(null);
                 transaction.getBuyer().getFollowers().clear();
                 transaction.getBuyer().getFollowing().clear();
                 transaction.getBuyer().setLikedListings(null);
             }

             if (transaction.getSeller() != null) {
                 transaction.getSeller().getCreatedListings().clear();
                 transaction.getSeller().setConversations(null);
                 transaction.getSeller().setCreditCards(null);
                 transaction.getSeller().setBuyerTransactions(null);
                 transaction.getSeller().setSellerTransactions(null);
                 transaction.getSeller().getFollowers().clear();
                 transaction.getSeller().getFollowing().clear();
                 transaction.getSeller().setLikedListings(null);
             }


             transaction.setDispute(null);

             if(transaction.getCreatedOn() != null){
                 transaction.getBuyerCard().setUser(null);
             }

             if(transaction.getListing()!= null){
                 transaction.getListing().setCreatedBy(null);
                 transaction.getListing().getLikedByUsers().clear();
                 transaction.getListing().getTransactions().clear();
                 transaction.getListing().getConversations().clear();
             }

            return Response.status(Status.OK).entity(dispute).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    
    @Path("retrieveUserDisputes")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUserDisputes(@QueryParam("username") String username, 
                                    @QueryParam("password") String password){
    
         try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
           
            List<Dispute> list = disputeSessionBean.retrieveUserDisputes(user.getUserId());
            
            for(Dispute dispute: list){
                Transaction transaction = dispute.getTransaction();
               if (transaction.getBuyer() != null) {
                    transaction.getBuyer().getCreatedListings().clear();
                    transaction.getBuyer().setConversations(null);
                    transaction.getBuyer().setCreditCards(null);
                    transaction.getBuyer().setBuyerTransactions(null);
                    transaction.getBuyer().setSellerTransactions(null);
                    transaction.getBuyer().getFollowers().clear();
                    transaction.getBuyer().getFollowing().clear();
                    transaction.getBuyer().setLikedListings(null);
                }
                
                if (transaction.getSeller() != null) {
                    transaction.getSeller().getCreatedListings().clear();
                    transaction.getSeller().setConversations(null);
                    transaction.getSeller().setCreditCards(null);
                    transaction.getSeller().setBuyerTransactions(null);
                    transaction.getSeller().setSellerTransactions(null);
                    transaction.getSeller().getFollowers().clear();
                    transaction.getSeller().getFollowing().clear();
                    transaction.getSeller().setLikedListings(null);
                }
                

                transaction.setDispute(null);

                if(transaction.getCreatedOn() != null){
                    transaction.getBuyerCard().setUser(null);
                }
                
                if(transaction.getListing()!= null){
                    transaction.getListing().setCreatedBy(null);
                    transaction.getListing().getLikedByUsers().clear();
                    transaction.getListing().getTransactions().clear();
                    transaction.getListing().getConversations().clear();
                }
            }
            
            GenericEntity<List<Dispute>> genericEntity = new GenericEntity<List<Dispute>>(list) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
        
    
    private ListingSessionBeanLocal lookupListingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ListingSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/ListingSessionBean!ejb.stateless.ListingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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

    private TransactionSessionBeanLocal lookupTransactionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TransactionSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/TransactionSessionBean!ejb.stateless.TransactionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DisputeSessionBeanLocal lookupDisputeSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DisputeSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/DisputeSessionBean!ejb.stateless.DisputeSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
