package com.changhf.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class UserLoginLog implements Serializable {

	private static final long serialVersionUID = -6920526436446712798L;
	private Integer id;
	private Integer userId;
	private String ip;
	private Date loginTime;

	public UserLoginLog(Integer userId, String ip) {
//		LocalDateTime date = LocalDateTime.now().withNano(0);
		Date date = new Date();
		this.userId = userId;
		this.ip = ip;
		this.loginTime = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
