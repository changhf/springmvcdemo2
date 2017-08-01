package com.changhf.utils.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存统一输出接口
 * @author 曾斌
 *
 */
public interface CacheService {
    /**
     * del
     * @param key
     */
    public void del(byte[] key);

    /**
     * del
     * @param key
     */
    public void del(String key);

    /**
     * set
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, int liveTime);

    /**
     * set
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, int liveTime);

    /**
     * set
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * setObj
     * @param key
     * @param obj
     */
    public void setObj(String key, Object obj);

    /**
     * getObj
     * @param key
     * @return
     */
    public Object getObj(String key);

    /**
     * set
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value);

    /**
     * get
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * get
     * @param key
     * @return
     */
    public byte[] get(byte[] key);

    /**
     * keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern);

    /**
     * exists
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * flushDB
     * @return
     */
    public String flushDB();

    /**
     * dbSize
     * @return
     */
    public long dbSize();

    /**
     * ping
     * @return
     */
    public String ping();

    /**
     * pipeGetList
     * @param keys
     * @return
     */
    public List<Object> pipeGetList(List<String> keys);

    /**
     * pipeGetMap
     * @param keyName
     * @param keys
     * @return
     */
    public Map<String, Object> pipeGetMap(String keyName, List<String> keys);

    /**
     * pipeSet
     * @param keyName
     * @param listContent
     */
    void pipeSet(String keyName, List<? extends Object> listContent);

    /**
     * saddObj
     * @param key
     * @param obj
     */
    public void saddObj(String key, Object obj);

    /**
     * sadd
     * @param key
     * @param value
     */
    public void sadd(byte[] key, byte[] value);

    /**
     * smembersObj
     * @param key
     * @return
     */
    public List<Object> smembersObj(String key);

    /**
     * smembers
     * @param key
     * @return
     */
    public Set<byte[]> smembers(byte[] key);

    /**
     * sremObj
     * @param key
     * @param obj
     */
    public void sremObj(String key, Object obj);

    /**
     * srem
     * @param key
     * @param value
     */
    public void srem(byte[] key, byte[] value);

}
