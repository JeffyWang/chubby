package com.jeffy.dao.impl;

import com.jeffy.bean.User;
import com.jeffy.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
@Transactional
public class UserDaoImpl extends CommonDaoImpl<User> implements UserDao {

}
