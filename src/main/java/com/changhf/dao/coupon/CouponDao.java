package com.changhf.dao.coupon;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CouponDao {
	 /**
     * 根据组id获取优惠码
     * @param groupId 组id
     * @return list
     */
    List<Map<String, Object>> getCouponCodeByGroupId(@Param(value = "groupId") int groupId);
}
