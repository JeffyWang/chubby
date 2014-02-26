package com.jeffy.service;

import com.jeffy.bean.User;
import com.jeffy.bo.Response;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User: jeffy
 * Date: 13-12-7
 * Time: 上午11:13
 */

@Path("/user")
public interface UserService {
    @GET
    @Path("/{id}")
    @Produces("application/json;charset=utf-8")
    public Response getUser(@PathParam("id") Long id);

    @GET
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response getUsers();

    @POST
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response saveUser(User user);

    @PUT
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response updateUser(User user);

    @DELETE
    @Path("/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteUser(@PathParam("id") Long id);
}
