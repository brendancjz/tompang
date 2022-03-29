/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.InvalidLoginCredentialsException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    
    public UserResource() {
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBean = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    @Path("userLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("username") String username, 
                                @QueryParam("password") String password) {
        try {
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            user.setPassword(null);
            user.setSalt(null);
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        }
    }
}
