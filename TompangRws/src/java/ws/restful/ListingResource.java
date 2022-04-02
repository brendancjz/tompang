/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Conversation;
import entity.Listing;
import entity.User;
import exception.CreateNewListingException;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateListingReq;
import ws.datamodel.UpdateListingReq;

/**
 * REST Web Service
 *
 * @author GuoJun
 */
@Path("Listing")
public class ListingResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final ListingSessionBeanLocal listingSessionBean;
    private final UserSessionBeanLocal userSessionBean;

    public ListingResource() {
        sessionBeanLookup = new SessionBeanLookup();

        userSessionBean = lookupUserSessionBeanLocal();
        listingSessionBean = lookupListingSessionBeanLocal();
    }

    @Path("retrieveAllListings")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllListings(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** ListingResource.retrieveAllProducts(): User " + user.getUsername() + " login remotely via web service");

            List<Listing> listings = listingSessionBean.retrieveAllListings();
            System.out.println(listings.size());
            for (Listing listing : listings) {
                User createdBy = listing.getCreatedBy();
                
                //createdBy.getCreatedListings().clear();
                
                listing.setCreatedBy(null);
                
//                if (createdBy == null)
//                    System.out.println("********** createdBy IS NULL");
//                
//                if (createdBy.getConversations() != null) {
//                    for (Conversation conversation : createdBy.getConversations()) {
//                        conversation.setCreatedBy(null);
//                    }
//                    createdBy.getConversations().clear();
//                
//                }
//                
//                if (createdBy.getCreatedListings() != null) {
//                    for (Listing createdListing : createdBy.getCreatedListings()) {
//                        createdListing.setCreatedBy(null);
//                    }
//                    createdBy.getCreatedListings().clear();
//
//                }

                
                if (listing.getConversations() != null) {
                    for (Conversation conversation : listing.getConversations()) {
                        conversation.getMessages().clear();
                    }
                    listing.getConversations().clear();
                }

                if (listing.getTransactions() != null) {
                    listing.getTransactions().clear();
                }
            }

            System.out.println(listings);

            GenericEntity<List<Listing>> genericEntity = new GenericEntity<List<Listing>>(listings) {
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
    public Response createListing(CreateListingReq createListingReq) {
        if (createListingReq != null) {
            try {
                User user = userSessionBean.userLogin(createListingReq.getUsername(), createListingReq.getPassword());

                Long listingId = listingSessionBean.createNewListing(createListingReq.getListing(), user.getUserId());

                return Response.status(Response.Status.OK).entity(listingId).build();
            } catch (InvalidLoginCredentialsException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            } catch (CreateNewListingException ex) {
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
    public Response updateListing(UpdateListingReq updateListingReq) {
        if (updateListingReq != null) {
            try {
                User user = userSessionBean.userLogin(updateListingReq.getUsername(), updateListingReq.getPassword());
                listingSessionBean.updateListingDetails(updateListingReq.getListing());

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
}
