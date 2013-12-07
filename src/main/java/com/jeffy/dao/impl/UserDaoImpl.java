package com.jeffy.dao.impl;

import com.jeffy.bean.User;
import com.jeffy.dao.UserDao;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl extends BaseDao<User, Long> implements UserDao {

}
