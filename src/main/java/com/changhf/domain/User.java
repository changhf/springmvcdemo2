package com.changhf.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = -126536700512038471L;
	private Integer id;
	private String userName;
	private String password;
	private String salt;
	private String mobile;
	private Date addTime;

	public User() {
	}

	public User(int id) {
		this.id = id;
	}

	public User(String mobile) {
		this.mobile = mobile;
	}

	public User(String userName, String mobile) {
		Date date = new Date();
		this.userName = userName;
		this.mobile = mobile;
		this.addTime = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
