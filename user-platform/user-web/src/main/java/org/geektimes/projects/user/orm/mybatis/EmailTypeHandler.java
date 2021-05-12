package org.geektimes.projects.user.orm.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailTypeHandler implements TypeHandler<Email> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Email parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Email getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Email getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Email getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
