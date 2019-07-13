package com.jtw.main.unified.dao;

import com.jtw.main.unified.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper
{
    List<User> findByRoleId(int roleId);

    List<User> findAll();

    User findByName(String username);

    void addUser(User user);

    void changePassword(String userName, String password, String salt);

    void changeRoleId(String userName, int roleId);

    void deleteUserByName(String userName);
}
