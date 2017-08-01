package com.changhf.service.coupon;

import java.util.List;
import java.util.Map;

public interface CouponService {
	/**
     * 根据组id获取优惠码
     * @param groupId 组id
     * @return list
     */
    List<Map<String, Object>> getCouponCodeByGroupId(Integer groupId);
}
