package com.changhf.core.concurrent;

import com.changhf.domain.User;
import com.changhf.domain.ValueEvent;

public class ConcurrentUtil {

	/**
	 * 环迅开户
	 * 
	 * @param user
	 * @param ips
	 */
	public static void register(User user) {
		ValueEvent event = new ValueEvent();
		event.setOperate("ipsRegister");
		event.setUser(user);
//		event.setIpsRegister(ips);
//		event.setResultFlag(resultFlag);
		JobQueue.getUserInstance().offer(event);
	}
}
