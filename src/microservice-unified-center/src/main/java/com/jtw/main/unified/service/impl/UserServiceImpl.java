package com.jtw.main.unified.service.impl;

import com.jtw.main.unified.Exception.ParameterException;
import com.jtw.main.unified.dao.UserMapper;
import com.jtw.main.unified.entity.User;
import com.jtw.main.unified.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findByRoleId(int roleId) {
        return userMapper.findByRoleId(roleId);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findByName(String username)
    {

        return userMapper.findByName(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addUser(@Param("user") User user)
    {
        if (user == null)
        {
            throw new ParameterException("parma user entity is null.");
        }
        userMapper.addUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changePassword(String userName, String password, String salt)
    {
        userMapper.changePassword(userName, password, salt);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changeRoleId(String userName, int roleId)
    {
        userMapper.changeRoleId(userName, roleId);
    }

    @Override
    public void deleteByUserName(String userName)
    {
        userMapper.deleteUserByName(userName);
    }


}
