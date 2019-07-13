package com.jtw.main.mybatis.po;

import org.apache.ibatis.type.Alias;

import java.io.UnsupportedEncodingException;

public class User {

	
	private int id;
	private String userName;
	private String password;
	private String email;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {

		try {
			this.userName = new String(userName.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", age=" + age +
				'}';
	}
}
