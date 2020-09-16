package com.givts.app.mapper;

import com.givts.app.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void insertUser(User user);
    void updateUserById(@Param("u") User user, long id);
    void deleteUserById(long id);
    User selectUserById(long id);

}
