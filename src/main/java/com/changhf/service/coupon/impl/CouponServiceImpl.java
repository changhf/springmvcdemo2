package com.changhf.service.coupon.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhf.dao.coupon.CouponDao;
import com.changhf.service.coupon.CouponService;
@Service("couponService")
public class CouponServiceImpl implements CouponService {
	@Autowired
    private CouponDao couponDao;
	@Override
	public List<Map<String, Object>> getCouponCodeByGroupId(Integer groupId) {
		return couponDao.getCouponCodeByGroupId(groupId);
	}

}
