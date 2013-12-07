package com.jeffy.service.impl;

import com.jeffy.bean.User;
import com.jeffy.dao.UserDao;
import com.jeffy.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffy
 * Date: 13-12-7
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */

@Transactional
public class UserServiceImpl implements UserService {
    private Logger log = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private  UserDao userDao = null;

    public Response getUser(@PathParam("id") Long id) {
        User user = userDao.find(id);

        log.debug("Get a user, the id is :" + id);
        return Response.ok(user).build();
    }

    public Response listUsers() {
        List<User> userList = userDao.findAll();

        log.debug("List all users");
        return Response.ok(userList).build();
    }

    public Response addUser(User user) {
        userDao.save(user);

        log.debug("Add a new user, the user's name is : " + user.getUserName());
        return Response.ok().build();
    }

    public Response updataUser(User user) {
        userDao.refresh(user);

        log.debug("Updata a user, the user's id id : " + user.getId());
        return Response.ok().build();
    }

    public Response deleteUser(@PathParam("id") Long id) {
        userDao.removeById(id);

        log.debug("Delete a user, the user's id is : " + id);
        return Response.ok().build();
    }
}
