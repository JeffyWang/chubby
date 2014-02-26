package com.jeffy.service.impl;

import com.jeffy.bean.User;
import com.jeffy.bo.Response;
import com.jeffy.dao.UserDao;
import com.jeffy.service.UserService;
import com.jeffy.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: jeffy
 * Date: 13-12-7
 * Time: 上午11:14
 */

//@Transactional
//@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger log = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private  UserDao userDao = null;

    public Response getUser(Long id) {
        System.out.println(id);
        Response response = ResponseUtil.getResponse();
        User user = (User)userDao.getById(User.class,id);

        response.setData(user);
        log.debug("Get a user, the user's id is :" + id);
        return response;
    }

    public Response getUsers() {
        Response response = ResponseUtil.getListResponse();
        Map<String, Object> equalCondition = null;
        Map<String, String> likeCondition = null;
        List<User> userList = userDao.getListAll(User.class, equalCondition, likeCondition);

        response.setData(userList);
        log.debug("List all users");
        return response;
    }

    public Response saveUser(User user) {
        Response response = ResponseUtil.saveResponse();
        User u = (User) userDao.save(user);

        response.setData(u);
        log.debug("Add a new user, the user's id is : " + user.getId());
        return response;
    }

    public Response updateUser(User user) {
        Response response = ResponseUtil.updateResponse();
        User u = (User)userDao.update(user);

        response.setData(u);
        log.debug("Updata a user, the user's id is : " + user.getId());
        return response;
    }

    public Response deleteUser(Long id) {
        Response response = ResponseUtil.deleteResponse();
        userDao.deleteById(User.class, id);
        Map<String, Long> data = new HashMap<String, Long>();
        data.put("id",id);

        response.setData(data);
        log.debug("Delete a user, the user's id is : " + id);
        return response;
    }
}
