package org.geektimes.projects.user.service;

import org.geektimes.projects.user.database.DataBaseInitialization;
import org.geektimes.projects.user.domain.User;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/3/1 22:57
 */
public class UserServiceImpl implements UserService{
    DataBaseInitialization dataBase = DataBaseInitialization.getInstance();

    public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
            "(%s ,%s ,%s ,%s);";

    @Override
    public boolean register(User user){
        try {
            Connection connection = dataBase.getConnection();
            Statement statement = connection.createStatement();
            String insertSql = String.format(INSERT_USER_DML_SQL, user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
            dataBase.releaseConnection();
            return statement.executeUpdate(insertSql)==1;
        } catch (Exception e) {
            return false;
        }
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
