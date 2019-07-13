package com.jtw.main.mybatis.dao;

import java.util.List;

import com.jtw.main.mybatis.po.Person;
import com.jtw.main.mybatis.po.User;

public interface UserDao {

	List<Person> queryAllUsers();
	void createUser(User user);
	void deleteUser(int id);
	int countCustomName(String userName);
}
