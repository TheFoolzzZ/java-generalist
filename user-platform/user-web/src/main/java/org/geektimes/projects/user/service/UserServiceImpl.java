package org.geektimes.projects.user.service;

import org.geektimes.projects.user.domain.User;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/3/1 22:57
 */
public class UserServiceImpl implements UserService{



    @Override
    public boolean register(User user) {
        return false;
    }

    @Override

    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}
