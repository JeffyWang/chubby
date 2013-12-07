package com.jeffy.service;

import com.jeffy.bean.User;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffy
 * Date: 13-12-7
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
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
    public Response listUsers();

    @POST
    @Path("/")
    @Produces("application/json;charset=utf-8")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response addUser(User user);

    @PUT
    @Path("/")
    @Produces("application/json;charset=utf-8")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response updataUser(User user);

    @DELETE
    @Path("/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteUser(@PathParam("id") Long id);

}
