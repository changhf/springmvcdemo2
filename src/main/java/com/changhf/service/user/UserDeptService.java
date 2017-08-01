package com.changhf.service.user;

import java.util.Map;

public interface UserDeptService {
	/**
     * 获取集团树
     * @param depth
     * @param roleEntId
     * @param roleDepId 
     * @return
     */
    public Map<String, Object> getDepTreeByDepth(Integer depth, Integer roleEntId, Integer roleDepId) ;
}
