package org.geektimes.projects.user.database;

import org.geektimes.projects.user.domain.User;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @description
 * @Author TheFool
 * @Date 2021/3/2 22:05
 */
public class OrmService {
    DataBaseInitialization dataBase = DataBaseInitialization.getInstance();

    public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
            "(%s ,%s ,%s ,%s);";

    public int register(User user) throws Exception {
        Connection connection = dataBase.getConnection();
        Statement statement = connection.createStatement();
        String insertSql = String.format(INSERT_USER_DML_SQL, user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
        dataBase.releaseConnection();
        return statement.executeUpdate(insertSql);
    }

}
