package com.jtw.main.unified.service;


import com.jtw.main.unified.entity.User;

import java.util.List;

public interface UserService
{
    List<User> findByRoleId(int roleId);

    List<User> findAll();

    User findByName(String username);

    void addUser(User user);

    void changePassword(String userName, String password, String salt);

    void changeRoleId(String userName, int roleId);

    void deleteByUserName(String userName);
}
