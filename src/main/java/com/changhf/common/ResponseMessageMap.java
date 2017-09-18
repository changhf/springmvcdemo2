package com.changhf.common;

import java.util.Map;

import com.changhf.utils.RecodeEnumUtils;
import com.google.common.collect.Maps;
/**
 * 请求返回信息
 * @author changhf
 *
 */
public class ResponseMessageMap {
	public static Map<String, Object> selfDefineMap(int recode, String msg) {
		Map<String, Object> rtnMap = Maps.newHashMap();
		rtnMap.put(Constants.MOBILE_RECODE, recode);
		rtnMap.put(Constants.MOBILE_MSG, msg);
		return rtnMap;
	}
	
	public static Map<String, Object> successMap() {
		Map<String, Object> rtnMap = Maps.newHashMap();
		rtnMap.put(Constants.MOBILE_RECODE, 1);
		rtnMap.put(Constants.MOBILE_MSG, "操作成功！");
		return rtnMap;
	}
	/**
     * 操作成功
     * @return
     */
    public static Map<String, Object> successMap(Object obj) {

        Map<String, Object> rtnMap = Maps.newHashMap();
        if (obj != null) {
            rtnMap.put(Constants.MOBILE_DATA, obj);
        }
        rtnMap.put(Constants.MOBILE_RECODE, 1);
        rtnMap.put(Constants.MOBILE_MSG, "操作成功！");
        return rtnMap;
    }
	/**
     * 数据库新增失败
     * @return
     */
    public static Map<String, Object> insertErrorMap() {
        Map<String, Object> rtnMap = Maps.newHashMap();
        rtnMap.put(Constants.MOBILE_RECODE, RecodeEnumUtils.INSERT_ERROR.getCode());
        rtnMap.put(Constants.MOBILE_MSG, RecodeEnumUtils.INSERT_ERROR.getMsg());
        return rtnMap;
    }
}
