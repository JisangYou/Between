package com.project.between.util;

import com.project.between.domain.User;

/**
 * Created by JisangYou on 2018-02-21.
 */

public class UserDao {

    private static User user;
    private static UserDao userDao = null;


    private UserDao() {
        user = new User();
    }

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserDao.user = user;
    }
}
