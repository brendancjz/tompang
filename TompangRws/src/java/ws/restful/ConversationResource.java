/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.ConversationSessionBeanLocal;
import ejb.stateless.MessageSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.Message;
import entity.User;
import exception.InvalidLoginCredentialsException;
import static java.lang.System.console;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.NewMessageReq;

/**
 * REST Web Service
 *
 * @author ignit
 */
@Path("Conversation")
public class ConversationResource {

    MessageSessionBeanLocal messageSessionBeanLocal = lookupMessageSessionBeanLocal();

    UserSessionBeanLocal userSessionBeanLocal = lookupUserSessionBeanLocal();

    ConversationSessionBeanLocal conversationSessionBeanLocal = lookupConversationSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConversationResource
     */
    public ConversationResource() {
    }

    @Path("retrieveBuyerConversations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBuyerConversations(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            User user = userSessionBeanLocal.userLogin(username, password);
            System.out.println(user.getUserId());
            System.out.println(user.getConversations());
            List<Conversation> conversationEntities = conversationSessionBeanLocal.retrieveAllBuyerConversations(user.getUserId());

            for (Conversation convo : conversationEntities) {
                if (convo.getCreatedBy() != null) {
                    convo.getCreatedBy().getCreatedListings().clear();
                    convo.getCreatedBy().setConversations(null);
                    convo.getCreatedBy().setCreditCards(null);
                    convo.getCreatedBy().setBuyerTransactions(null);
                    convo.getCreatedBy().setSellerTransactions(null);
                    convo.getCreatedBy().getFollowers().clear();
                    convo.getCreatedBy().getFollowing().clear();
                    convo.getCreatedBy().setLikedListings(null);
                }
                if (convo.getListing() != null) {
                    convo.getListing().setCreatedBy(null);
                    convo.getListing().getLikedByUsers().clear();
                    convo.getListing().getTransactions().clear();
                    convo.getListing().getConversations().clear();
                }
                if (convo.getSeller() != null) {
                    convo.getSeller().getCreatedListings().clear();
                    convo.getSeller().setConversations(null);
                    convo.getSeller().setCreditCards(null);
                    convo.getSeller().setBuyerTransactions(null);
                    convo.getSeller().setSellerTransactions(null);
                    convo.getSeller().getFollowers().clear();
                    convo.getSeller().getFollowing().clear();
                    convo.getSeller().setLikedListings(null);
                }
            }

            GenericEntity<List<Conversation>> genericEntity = new GenericEntity<List<Conversation>>(conversationEntities) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            ex.printStackTrace();
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveSellerConversations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSellerConversations(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            User user = userSessionBeanLocal.userLogin(username, password);
            System.out.println(user.getUserId());
            System.out.println(user.getConversations());
            List<Conversation> conversationEntities = conversationSessionBeanLocal.retrieveAllSellerConversations(user.getUserId());

            for (Conversation convo : conversationEntities) {
                if (convo.getCreatedBy() != null) {
                    convo.getCreatedBy().getCreatedListings().clear();
                    convo.getCreatedBy().setConversations(null);
                    convo.getCreatedBy().setCreditCards(null);
                    convo.getCreatedBy().setBuyerTransactions(null);
                    convo.getCreatedBy().setSellerTransactions(null);
                    convo.getCreatedBy().getFollowers().clear();
                    convo.getCreatedBy().getFollowing().clear();
                    convo.getCreatedBy().setLikedListings(null);
                }
                if (convo.getListing() != null) {
                    convo.getListing().setCreatedBy(null);
                    convo.getListing().getLikedByUsers().clear();
                    convo.getListing().getTransactions().clear();
                    convo.getListing().getConversations().clear();
                }
                if (convo.getSeller() != null) {
                    convo.getSeller().getCreatedListings().clear();
                    convo.getSeller().setConversations(null);
                    convo.getSeller().setCreditCards(null);
                    convo.getSeller().setBuyerTransactions(null);
                    convo.getSeller().setSellerTransactions(null);
                    convo.getSeller().getFollowers().clear();
                    convo.getSeller().getFollowing().clear();
                    convo.getSeller().setLikedListings(null);
                }
            }

            GenericEntity<List<Conversation>> genericEntity = new GenericEntity<List<Conversation>>(conversationEntities) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            ex.printStackTrace();
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveConversation/{convoId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSellerConversations(@QueryParam("username") String username, @QueryParam("password") String password, @PathParam("convoId") Long convoId) {
        try {
            User user = userSessionBeanLocal.userLogin(username, password);
            System.out.println(user.getUserId());
            System.out.println(user.getConversations());
            Conversation convo = conversationSessionBeanLocal.getConversationByConvoId(convoId);

            if (convo.getCreatedBy() != null) {
                convo.getCreatedBy().getCreatedListings().clear();
                convo.getCreatedBy().setConversations(null);
                convo.getCreatedBy().setCreditCards(null);
                convo.getCreatedBy().setBuyerTransactions(null);
                convo.getCreatedBy().setSellerTransactions(null);
                convo.getCreatedBy().getFollowers().clear();
                convo.getCreatedBy().getFollowing().clear();
                convo.getCreatedBy().setLikedListings(null);
            }
            if (convo.getListing() != null) {
                convo.getListing().setCreatedBy(null);
                convo.getListing().getLikedByUsers().clear();
                convo.getListing().getTransactions().clear();
                convo.getListing().getConversations().clear();
            }
            if (convo.getSeller() != null) {
                convo.getSeller().getCreatedListings().clear();
                convo.getSeller().setConversations(null);
                convo.getSeller().setCreditCards(null);
                convo.getSeller().setBuyerTransactions(null);
                convo.getSeller().setSellerTransactions(null);
                convo.getSeller().getFollowers().clear();
                convo.getSeller().getFollowing().clear();
                convo.getSeller().setLikedListings(null);
            }

            GenericEntity<Conversation> genericEntity = new GenericEntity<Conversation>(convo) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            ex.printStackTrace();
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewMessageAndUpdateConversation(NewMessageReq newMessageReq) {
        System.out.println("Method called");
        if (newMessageReq != null) {
            try {
                System.out.println("ENTER TRY");
                Long newMessageId = messageSessionBeanLocal.createNewMessage(newMessageReq.getNewMessage());
                System.out.println(newMessageReq.getConvoId());
                if (newMessageReq.getConvoId() != null) {
                    System.out.println("CALLING CONVO BEAN");
                    conversationSessionBeanLocal.addMessage(newMessageReq.getConvoId(), messageSessionBeanLocal.getMessageByMessageId(newMessageId));
                }
                return Response.status(Response.Status.OK).entity(newMessageReq.getConvoId()).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid add message request").build();
        }
    }

    private ConversationSessionBeanLocal lookupConversationSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ConversationSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/ConversationSessionBean!ejb.stateless.ConversationSessionBeanLocal");
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

    private MessageSessionBeanLocal lookupMessageSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MessageSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/MessageSessionBean!ejb.stateless.MessageSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
