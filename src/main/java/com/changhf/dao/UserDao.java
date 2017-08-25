package com.changhf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.changhf.dao.base.IBaseDao;
import com.changhf.domain.User;
import com.changhf.domain.UserLoginLog;
import com.changhf.plugin.page.Page;

public interface UserDao extends IBaseDao<User>{
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id);
	/**
	 * 分页查询用户列表
	 * @return
	 */
	public List<User> listUser(@Param(value="page") Page<?> page);
	/**
	 * 查询总记录数
	 * @return
	 */
	public Integer countUser();
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public User findUserByMobile(String mobile);
	/**
	 * 根据id查询用户
	 * @return
	 */
//	public User getUserByMobile(String mobile);

	public int addUser(User user);
	/**
	 * 修改用户信息
	 */
	public int updateUser(User user);
	/**
	 * 删除用户信息
	 */
	public int delUserById(int id);
	/**
	 * 插入用户登录日志
	 * @return
	 */
	public int addUserLoginLog(UserLoginLog log);
	
}
