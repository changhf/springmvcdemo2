package com.changhf.dao.user;

import java.util.List;

import com.changhf.domain.UserDepartment;

public interface UserDeptDao {
	List<UserDepartment> getDeptRootList();
}
