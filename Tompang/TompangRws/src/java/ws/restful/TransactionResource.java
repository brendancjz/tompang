/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.TransactionSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Transaction;
import entity.User;
import exception.CreateNewTransactionException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateTransactionReq;
import ws.datamodel.UpdateTransactionReq;

/**
 * REST Web Service
 *
 * @author GuoJun
 */
@Path("Transaction")
public class TransactionResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
//    private final ListingSessionBeanLocal listingSessionBean;
    private final UserSessionBeanLocal userSessionBean;
    private final TransactionSessionBeanLocal transactionSessionBean;

    public TransactionResource() {
        sessionBeanLookup = new SessionBeanLookup();
        transactionSessionBean = lookupTransactionSessionBeanLocal();
        userSessionBean = lookupUserSessionBeanLocal();
//        listingSessionBean = lookupListingSessionBeanLocal();
    }

    @Path("retrieveAllUserTransactions")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUserTransactions(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);

            List<Transaction> transactions = transactionSessionBean.retrieveTransactionsByUserId(user.getUserId());

            for (Transaction transaction : transactions) {

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

                if (transaction.getDispute() != null) {
                    transaction.getDispute().setTransaction(null);
                }

                if (transaction.getCreatedOn() != null) {
                    transaction.getBuyerCard().setUser(null);
                }

                if (transaction.getListing() != null) {
                    transaction.getListing().setCreatedBy(null);
                    transaction.getListing().getLikedByUsers().clear();
                    transaction.getListing().getTransactions().clear();
                    transaction.getListing().getConversations().clear();
                }

            }

            System.out.println(transactions);

            GenericEntity<List<Transaction>> genericEntity = new GenericEntity<List<Transaction>>(transactions) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            ex.printStackTrace();
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(CreateTransactionReq createTransactionReq) {
        if (createTransactionReq != null) {
            try {
                User user = userSessionBean.userLogin(createTransactionReq.getUsername(), createTransactionReq.getPassword());
                Long transactionId = transactionSessionBean.createNewTransaction(user.getUserId(), createTransactionReq.getListingId(), createTransactionReq.getTransaction());

                return Response.status(Response.Status.OK).entity(transactionId).build();
            } catch (InvalidLoginCredentialsException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            } catch (CreateNewTransactionException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new listing request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeTransaction(UpdateTransactionReq updateTransactionReq) {
        if (updateTransactionReq != null) {
            try {
                User user = userSessionBean.userLogin(updateTransactionReq.getUsername(), updateTransactionReq.getPassword());

                Transaction transaction = transactionSessionBean.getTransactionByTransactionId(updateTransactionReq.getTransactionId());
                
                System.out.println("seller: " + transaction.getSeller().getUserId());
                System.out.println("user: " + user.getUserId());
                if (transaction.getBuyer().getUserId() != user.getUserId()) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Transaction does not belong to user").build();
                } else {
                    transactionSessionBean.updateTransactionIsCompleted(updateTransactionReq.getTransactionId(), Boolean.TRUE);
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
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update listing request").build();
        }
    }

    @Path("acceptTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptTransaction(UpdateTransactionReq updateTransactionReq) {
        if (updateTransactionReq != null) {
            try {
                User user = userSessionBean.userLogin(updateTransactionReq.getUsername(), updateTransactionReq.getPassword());
                System.out.println(updateTransactionReq.getTransactionId());
                Transaction transaction = transactionSessionBean.getTransactionByTransactionId(updateTransactionReq.getTransactionId());
                System.out.println(transaction.getSeller().getUserId());
                System.out.println(user.getUserId());
                if (transaction.getSeller().getUserId() != user.getUserId()) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Transaction does not belong to user").build();
                } else {
                    transactionSessionBean.updateTransactionIsAccepted(updateTransactionReq.getTransactionId());
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
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update listing request").build();
        }
    }

    @Path("rejectTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectTransaction(UpdateTransactionReq updateTransactionReq) {
        if (updateTransactionReq != null) {
            try {
                User user = userSessionBean.userLogin(updateTransactionReq.getUsername(), updateTransactionReq.getPassword());

                Transaction transaction = transactionSessionBean.getTransactionByTransactionId(updateTransactionReq.getTransactionId());

                if (transaction.getSeller().getUserId() != user.getUserId()) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Transaction does not belong to user").build();
                } else {
                    transactionSessionBean.updateTransactionIsRejected(updateTransactionReq.getTransactionId());
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
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update listing request").build();
        }
    }

    @Path("retrieveTransaction/{transactionId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTransaction(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("transactionId") Long transactionId) {

        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);

            Transaction transaction = transactionSessionBean.getTransactionByTransactionId(transactionId);

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

            if (transaction.getDispute() != null) {
                transaction.getDispute().setTransaction(null);
            }

            if (transaction.getCreatedOn() != null) {
                transaction.getBuyerCard().setUser(null);
            }

            if (transaction.getListing() != null) {
                transaction.getListing().setCreatedBy(null);
                transaction.getListing().getLikedByUsers().clear();
                transaction.getListing().getTransactions().clear();
                transaction.getListing().getConversations().clear();
            }

            System.out.println(transaction);

            return Response.status(Status.OK).entity(transaction).build();
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

}
