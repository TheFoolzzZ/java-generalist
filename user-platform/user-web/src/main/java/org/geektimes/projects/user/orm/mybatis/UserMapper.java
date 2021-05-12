package org.geektimes.projects.user.orm.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.geektimes.projects.user.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAll();
}
