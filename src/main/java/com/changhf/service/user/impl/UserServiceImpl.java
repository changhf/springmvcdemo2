package com.changhf.service.user.impl;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.changhf.common.Constants;
import com.changhf.common.ResponseMessageMap;
import com.changhf.common.session.SessionCache;
import com.changhf.core.concurrent.ConcurrentUtil;
import com.changhf.dao.UserDao;
import com.changhf.domain.User;
import com.changhf.domain.UserLoginLog;
import com.changhf.domain.ValueEvent;
import com.changhf.plugin.page.Page;
import com.changhf.plugin.sms.SmsMessageProxyService;
import com.changhf.service.rocketmq.PullConsumer;
import com.changhf.service.user.UserService;
import com.changhf.utils.MD5Utils;
import com.changhf.utils.RandomNumUtil;
import com.changhf.utils.RegExUtils;
import com.changhf.utils.redis.RedisService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected SmsMessageProxyService smsMessageProxyService;
    @Autowired
    protected RedisService redisService;
    @Autowired
    protected PullConsumer mqconsumer;

    public Map<String, Object> findUserByMobile(String mobile) {
        User user = userDao.findUserByMobile(mobile);
        Map<String, Object> map = Maps.newHashMap();
        map.put("user", user);
        return map;
    }

    public Map<String, Object> getUserById(Integer id) {
        User user = userDao.getUserById(id);
        Map<String, Object> map = Maps.newHashMap();
        map.put("data", user);
        return map;
    }

    @Override
    public Map<String, Object> listUser(Integer pageSize, Integer pageOffset) {
        Map<String, Object> rtnMap = Maps.newHashMap();
        Page<?> page = new Page<>(pageOffset, pageSize);
        List<User> userList = userDao.listUser(page);
        //查询总记录数
        Integer total = userDao.countUser();
        rtnMap.put("list", userList);
        rtnMap.put("total", total);
        return ResponseMessageMap.successMap(rtnMap);
    }

    public Map<String, Object> pwdLogin(String mobile, String password, String sessionId, String ip) {

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return ResponseMessageMap.selfDefineMap(-1, "用户名密码不能为空。");
        }
        if (!RegExUtils.checkPhone(mobile)) {
            return ResponseMessageMap.selfDefineMap(-1, "手机号码不符合规则");
        }
        // 用户是否已注册
        User user = userDao.findUserByMobile(mobile);
        if (user == null || StringUtils.isEmpty(user.getMobile())) {
            return ResponseMessageMap.selfDefineMap(-1, "该手机号尚未注册。");
        }
        String salt = user.getSalt() == null ? "" : user.getSalt();
        password = MD5Utils.encodePassword(password + salt);
        if (user.getMobile().equals(mobile) && user.getPassword().equals(password)) {
       	Map<String, Object> retMap = Maps.newHashMap();
            // 用户登陆成功操作
            this.saveUserLoginInfo(user.getId(), ip, sessionId);
            // 获取最后一次登录成功时间，往前推5分钟，验证登录条数
//            validateLoginResult(userInfo.getMemberId(), userInfo.getMemberPhone(), 0, 5, sn, pwd);

            // handelActivityCoupon(mobile);
           retMap.put("userId", user.getId());
           retMap.put("sessionId", sessionId);
            return ResponseMessageMap.successMap(retMap);
        } else {
            return ResponseMessageMap.selfDefineMap(-1, "用户名或密码不正确。");
        }
        //主动拉去消息
//		handelActivityCoupon(mobile);
//		return null;
    }

    private Map<String, Object> saveUserLoginInfo(Integer userId, String ip, String sessionId) {
		SessionCache sessionCache = (SessionCache) redisService.getObj(sessionId);
        if (sessionCache == null) {
            sessionCache = new SessionCache();
            sessionCache.setSessionId(sessionId);
            redisService.setObj(sessionId, sessionCache);
            // redisService.set(sessionKey, sessionId);
        }
        int result = userDao.addUserLoginLog(new UserLoginLog(userId, ip));
        if (result <= 0) {
            return ResponseMessageMap.insertErrorMap();
        }
        return ResponseMessageMap.successMap();

    }

    @Override
    public Map<String, Object> checkMobileExist(String mobile) {
        User user = userDao.findUserByMobile(mobile);
        if (user == null) {
            String content = RandomNumUtil.random6();
            smsMessageProxyService.sendSMS(mobile, content, "hzgzyd", "gzyd#ipi");
            //将验证码放入缓存中的sessionCache
            String codeKey = Constants.SEND_CODE_USER_REGISTER + mobile;
            SessionCache cache = (SessionCache) redisService.getObj(codeKey);
            if (cache == null) {
                cache = new SessionCache();
            }
            cache.setAttribute(codeKey, content);
            redisService.setObj(codeKey, cache);

            return ResponseMessageMap.selfDefineMap(1, "发送验证码成功。");
        } else {
            return ResponseMessageMap.selfDefineMap(-1, "该手机号已注册。");
        }
    }

    public Map<String, Object> register(String mobile, String userName, String password) {
        Map<String, Object> resultMap = validateMobileAndPassword(mobile, password);
        if (resultMap != null) {
            return resultMap;
        }
        SessionCache cache = (SessionCache) redisService.getObj(Constants.SEND_CODE_USER_REGISTER + mobile);
        boolean verifyResult = false;
        if (null != cache.getAttribute(Constants.VERIFY_USER_REGISTER_CODE_RESULT + mobile)) {
            verifyResult = (boolean) cache.getAttribute(Constants.VERIFY_USER_REGISTER_CODE_RESULT + mobile);
        }
        if (!verifyResult) {
            return ResponseMessageMap.selfDefineMap(-1, "未通过验证码验证");
        }
        User user = new User(userName, mobile);
        //对密码加盐
        String salt = RandomNumUtil.random4();
        user.setPassword(MD5Utils.encodePassword(password + salt));
        user.setSalt(salt);
        //
//		int result = userDao.addUser(user);
//		if (result < 0) {
//			ResponseMessageMap.selfDefineMap(-1, "用户添加失败。");
//		}

        ValueEvent event = new ValueEvent();
        event.setUser(user);
        ConcurrentUtil.register(user);
        return ResponseMessageMap.successMap();
    }

    private Map<String, Object> validateMobileAndPassword(String mobile, String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return ResponseMessageMap.selfDefineMap(-1, "手机号或密码不能为空。");
        }
        // TODO 手机号 密码规则校验
        User user = userDao.findUserByMobile(mobile);
        if (user != null) {
            return ResponseMessageMap.selfDefineMap(-1, "该手机号已注册。");
        }
        return null;
    }

    public Map<String, Object> updateUser(String mobile, String password) {
//		String codeStr = redisService.getObj(SEND_CODE_FIND_PWD + mobile).toString();
        SessionCache cache = (SessionCache) redisService.getObj(Constants.SEND_CODE_FIND_PWD + mobile);
        boolean verifyResult = (boolean) cache.getAttribute(Constants.VERIFY_FIND_PWD_CODE_RESULT + mobile);
        if (!verifyResult) {
            return ResponseMessageMap.selfDefineMap(-1, "未通过验证码验证");
        }
        User user = new User(mobile);
        user.setPassword(MD5Utils.encodePassword(password));
        int result = userDao.updateUser(user);
        if (result < 0) {
            return ResponseMessageMap.selfDefineMap(-1, "密码重置失败。");
        }
        redisService.setObj("user", 1234);
        return ResponseMessageMap.successMap();
    }

    public Map<String, Object> delUserById(int id) {
        int result = userDao.delUserById(id);
        if (result < 0) {
            return ResponseMessageMap.selfDefineMap(-1, "用户删除失败。");
        }

        return ResponseMessageMap.successMap();
    }

    @Override
    public Map<String, Object> findPassword(String mobile) {
        String content = RandomNumUtil.random6();
        String result = smsMessageProxyService.sendSMS(mobile, content, "hzgzyd", "gzyd#ipi");
        if (result.isEmpty()) {
            return ResponseMessageMap.selfDefineMap(-1, "验证码发送失败。");
        } else {
            //将验证码放入缓存中的sessionCache
            String codeKey = Constants.SEND_CODE_FIND_PWD + mobile;
            SessionCache cache = (SessionCache) redisService.getObj(codeKey);
            if (cache == null) {
                cache = new SessionCache();
            }
            cache.setAttribute(codeKey, content);
            redisService.setObj(codeKey, cache);
            return ResponseMessageMap.selfDefineMap(1, "发送验证码成功。");
        }
    }

    private void handelActivityCoupon(String mobile) {
        //判断用户是否有参与权限
//        boolean flag = appUserManager.hasPemission(userId);
        if (true) {
//            boolean isNew = appUserManager.isNewUser(userId);
            try {
                mqconsumer.consume(mobile);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
//            Map<String, Object> map = orderManager.grantLoginCouponCode(userId, isNew, "登录发券", 142);
//            if (null != map) {
//                String prizeName = isNew ? "优惠券100元" : "优惠券70元";
//                inviteRewordsActivityManager.insertAwardLog(userId, prizeName);
//            }
        }
    }


}
