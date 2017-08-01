package com.changhf.service.user;

import java.util.Map;

public interface UserService {
	/**
	 * 根据手机号查询用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> findUserByMobile(String mobile);
	/**
	 * 根据id查询用户信息
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> getUserById(Integer id);
	/**
	 * 分页查询用户信息
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> listUser(Integer pageSize,Integer pageOffset);
	/**
	 * 手机号是否已被注册
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> checkMobileExist(String mobile);
	/**
	 * 用户注册
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> register(String mobile, String userName, String password);
	/**
	 * 删除用户
	 * @return
	 */
	public Map<String, Object> delUserById(int id);
	/**
	 * 用户登录接口
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> pwdLogin(String mobile, String password, String sessionId,String ip);
	/**
	 * 修改用户
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> updateUser(String mobile, String password);
	/**
	 * 向用户发送验证短信
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> findPassword(String mobile);
	
}
