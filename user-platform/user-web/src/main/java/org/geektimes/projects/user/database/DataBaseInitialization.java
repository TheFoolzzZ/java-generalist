package org.geektimes.projects.user.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/3/2 21:52
 */
public class DataBaseInitialization {

    public static DataBaseInitialization getInstance() {
        return new DataBaseInitialization();
    }

    private DataBaseInitialization() {
        try {
            initial();
        } catch (Exception e) {
            System.out.println(String.format("[DataBaseService]初始化发生异常，原因为：%s,打印堆栈：%s",e.getMessage(),e));
        }
    }

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    private void initial() throws Exception {
        String databaseURL = "jdbc:derby:/db/user-platform;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);
        Statement statement = connection.createStatement();
        // 创建 users 表
        System.out.println("初始化任务，创建users表，结果：" + statement.execute(CREATE_USERS_TABLE_DDL_SQL));
        statement.close();
    }


    public void releaseConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
