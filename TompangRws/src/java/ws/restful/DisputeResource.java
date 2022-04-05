/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.DisputeSessionBeanLocal;
import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.TransactionSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.CreateNewDisputeException;
import exception.InvalidLoginCredentialsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateDisputeReq;

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
                
                Long disputeId = disputeSessionBean.createNewDispute(createDisputeReq.getTransactionId(), createDisputeReq.getDispute());

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
