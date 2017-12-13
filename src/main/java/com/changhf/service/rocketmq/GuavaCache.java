package com.changhf.service.rocketmq;

import com.changhf.dao.UserDao;
import com.changhf.domain.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GuavaCache {
    @Autowired
    private UserDao userDao;

    public LoadingCache<String, User> userCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, User>() {
                        public User load(String key) throws Exception {
                            return userDao.findUserByMobile(key);
                        }
                    });

}
