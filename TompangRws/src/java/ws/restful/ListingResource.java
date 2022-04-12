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
import entity.Transaction;
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
import javax.ws.rs.DELETE;
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

            List<Listing> listings = listingSessionBean.retrieveAllAvailableListings();

            for (Listing listing : listings) {

                if (listing.getCreatedBy() != null) {
                    listing.getCreatedBy().getCreatedListings().clear();
                    listing.getCreatedBy().setConversations(null);
                    listing.getCreatedBy().setCreditCards(null);
                    listing.getCreatedBy().setBuyerTransactions(null);
                    listing.getCreatedBy().setSellerTransactions(null);
                    listing.getCreatedBy().setFollowers(null);
                    listing.getCreatedBy().setFollowing(null);
                    listing.getCreatedBy().setLikedListings(null);
                }

                if (listing.getConversations() != null) {
                    for (Conversation conversation : listing.getConversations()) {
                        conversation.setListing(null);
                        conversation.setCreatedBy(null);
                        conversation.getMessages().clear();
                    }
                }

                if (listing.getTransactions() != null) {
                    for (Transaction transaction : listing.getTransactions()) {
                        transaction.setListing(null);
                        transaction.setBuyer(null);
                        transaction.setSeller(null);
                        transaction.setDispute(null);
                        transaction.setCreditCard(null);
                    }
                }

                if (listing.getLikedByUsers() != null) {
                    for (User likedByUser : listing.getLikedByUsers()) {
                        likedByUser.getCreatedListings().clear();
                        likedByUser.setConversations(null);
                        likedByUser.setCreditCards(null);
                        likedByUser.setBuyerTransactions(null);
                        likedByUser.setSellerTransactions(null);
                        likedByUser.setFollowers(null);
                        likedByUser.setFollowing(null);
                        likedByUser.setLikedListings(null);
                    }
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

    @Path("retrieveAllUserListings")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUserListings(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);

            List<Listing> listings = listingSessionBean.retrieveUserListings(username);

            for (Listing listing : listings) {
                System.out.println("&&&& " + listing.getTitle());

                if (listing.getCreatedBy() != null) {
                    listing.getCreatedBy().setCreatedListings(null);
                    listing.getCreatedBy().setConversations(null);
                    listing.getCreatedBy().setCreditCards(null);
                    listing.getCreatedBy().setBuyerTransactions(null);
                    listing.getCreatedBy().setSellerTransactions(null);
                    listing.getCreatedBy().setFollowers(null);
                    listing.getCreatedBy().setFollowing(null);
                    listing.getCreatedBy().setLikedListings(null);
                }

                if (listing.getConversations() != null) {
                    for (Conversation conversation : listing.getConversations()) {
                        conversation.setListing(null);
                        conversation.setCreatedBy(null);
                        conversation.setMessages(null);
                    }
                }

                if (listing.getTransactions() != null) {
                    for (Transaction transaction : listing.getTransactions()) {
                        transaction.setListing(null);
                        transaction.setBuyer(null);
                        transaction.setSeller(null);
                        transaction.setDispute(null);
                        transaction.setCreditCard(null);
                    }
                }

                if (listing.getLikedByUsers() != null) {
                    for (User likedByUser : listing.getLikedByUsers()) {
                        likedByUser.setCreatedListings(null);
                        likedByUser.setConversations(null);
                        likedByUser.setCreditCards(null);
                        likedByUser.setBuyerTransactions(null);
                        likedByUser.setSellerTransactions(null);
                        likedByUser.setFollowers(null);
                        likedByUser.setFollowing(null);
                        likedByUser.setLikedListings(null);
                    }
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
                
                System.out.println(updateListingReq.getListing().getPrice());
                System.out.println(updateListingReq.getListing().getPhotos());
                System.out.println(updateListingReq.getListing().getNumOfLikes());
                System.out.println(updateListingReq.getListing().getCity());
                System.out.println(updateListingReq.getListing().getCategory());
                System.out.println(updateListingReq.getListing().getCreatedBy() == null);
                System.out.println(updateListingReq.getListing().getDescription());
                System.out.println(updateListingReq.getListing().getExpectedArrivalDate() == null);
                
                
                listingSessionBean.updateListingDetails(updateListingReq.getListing());
                
                System.out.println("Successful update of listing");
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

    @Path("retrieveListing/{listingId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveListing(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("listingId") Long listingId) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** ListingResource.retrieveAllProducts(): User " + user.getUsername() + " login remotely via web service");

            Listing listing = listingSessionBean.getListingByListingId(listingId);

            if (listing.getCreatedBy() != null) {
                listing.getCreatedBy().getCreatedListings().clear();
                listing.getCreatedBy().setConversations(null);
                listing.getCreatedBy().setCreditCards(null);
                listing.getCreatedBy().setBuyerTransactions(null);
                listing.getCreatedBy().setSellerTransactions(null);
                listing.getCreatedBy().getFollowers().clear();
                listing.getCreatedBy().getFollowing().clear();
                listing.getCreatedBy().setLikedListings(null);
            }

            if (listing.getConversations() != null) {
                for (Conversation conversation : listing.getConversations()) {
                    conversation.setListing(null);
                    conversation.setCreatedBy(null);
                    conversation.getMessages().clear();
                }
            }

            if (listing.getTransactions() != null) {
                for (Transaction transaction : listing.getTransactions()) {
                    transaction.setListing(null);
                    transaction.setBuyer(null);
                    transaction.setSeller(null);
                    transaction.setDispute(null);
                    transaction.setCreditCard(null);
                }
            }

            if (listing.getLikedByUsers() != null) {
                for (User likedByUser : listing.getLikedByUsers()) {
                    likedByUser.getCreatedListings().clear();
                    likedByUser.setConversations(null);
                    likedByUser.setCreditCards(null);
                    likedByUser.setBuyerTransactions(null);
                    likedByUser.setSellerTransactions(null);
                    likedByUser.setFollowers(null);
                    likedByUser.setFollowing(null);
                    likedByUser.setLikedListings(null);
                }
            }

            System.out.println(listing);

            GenericEntity<Listing> genericEntity = new GenericEntity<Listing>(listing) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("/{listingId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteListing(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("listingId") Long listingId) {
        try {
            User user = userSessionBean.userLogin(username, password);

            listingSessionBean.deleteListing(listingId);
            return Response.status(Status.OK).build();
            
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @Path("likeListing/{listingId}/{userId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeListing(@QueryParam("username") String username,
            @QueryParam("password") String password, @PathParam("listingId") Long listingId, @PathParam("userId") Long userId) {
        System.out.println("hi " + listingId + " i am getting liked!");
        try {
            listingSessionBean.likeListing(userId, listingId);
            return Response.status(Response.Status.OK).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

    }

    @Path("unlikeListing/{listingId}/{userId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlikeListing(@QueryParam("username") String username,
            @QueryParam("password") String password, @PathParam("listingId") Long listingId, @PathParam("userId") Long userId) {
        try {
            listingSessionBean.unlikeListing(listingId, userId);
            return Response.status(Response.Status.OK).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
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
