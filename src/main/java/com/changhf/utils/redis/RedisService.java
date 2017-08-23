package com.changhf.utils.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import com.changhf.common.Constants;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * RedisService
 */
@Service("cacheService")
public class RedisService implements CacheService, InitializingBean {

	// jedis连接池
	private static JedisPool jedisPool = null;

	@Resource(name = "jedisConnectionFactory")
	private JedisConnectionFactory jedisConnectionFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		jedisPool = new JedisPool(jedisConnectionFactory.getPoolConfig(), jedisConnectionFactory.getHostName(),
				jedisConnectionFactory.getPort(), jedisConnectionFactory.getTimeout(),
				jedisConnectionFactory.getPassword());
	}

	/**
	 * getJedis
	 * 
	 * @return
	 */
	public Jedis getJedis() {
		// init();
		if (jedisPool != null) {
			return jedisPool.getResource();
		} else {
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 通过key删除（字节）
	 * 
	 * @param key
	 */
	@Override
	public void del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	@Override
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	@Override
	public void set(byte[] key, byte[] value, int liveTime) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	@Override
	public void set(String key, String value, int liveTime) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
		} finally {
			this.returnResource(jedis);
		}
	}

	/***
	 * <pre>
	 * 设置对象
	 * </pre>
	 * 
	 * @param key
	 * @param obj
	 */
	@Override
	public void setObj(String key, Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			byte[] byteArray = bos.toByteArray();
			oos.close();
			bos.close();
			this.set(key.getBytes(), byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * <pre>
	 * 获取对象
	 * </pre>
	 * 
	 * @param key
	 * @param key
	 */
	@Override
	public Object getObj(String key) {
		try {
			byte[] byteArray = this.get(key.getBytes());
			if (null == byteArray) {
				return null;
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
			ObjectInputStream inputStream = new ObjectInputStream(bis);
			Object readObject = (Object) inputStream.readObject();
			inputStream.close();
			bis.close();
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加key value (字节)(序列化)
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.get(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取redis value (byte [] )(反序列化)
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.get(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	@Override
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.keys(pattern);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.exists(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	@Override
	public String flushDB() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.flushDB();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 查看redis里有多少数据
	 */
	@Override
	public long dbSize() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.dbSize();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	@Override
	public String ping() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.ping();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 批量操作:主键格式,keyName&+keyValue
	 * 
	 * @param keyName
	 * @param listContent
	 */
	@Override
	public void pipeSet(String keyName, List<? extends Object> listContent) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			Pipeline pipe = jedis.pipelined();
			ByteArrayOutputStream bos = null;
			ObjectOutputStream oos = null;
			for (Object obj : listContent) {
				bos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(bos);
				oos.writeObject(obj);
				byte[] byteArray = bos.toByteArray();
				// 获取数据主键
				String fieldNameTemp = "get" + keyName.toString().substring(0, 1).toUpperCase()
						+ keyName.toString().substring(1);
				Method method = obj.getClass().getMethod(fieldNameTemp);
				Object keyValue = method.invoke(obj);
				// 批量插入
				pipe.set((keyName + Constants.SEPERATOR_FLAG + keyValue).getBytes(), byteArray);
			}
			if (null != oos) {
				oos.close();
			}
			if (null != bos) {
				bos.close();
			}
			pipe.sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 根据主键批量获取数据:主键格式,keyName&+keyValue
	 * 
	 * @param keyName
	 * @param keys
	 * @return
	 */
	@Override
	public Map<String, Object> pipeGetMap(String keyName, List<String> keys) {
		Map<String, Object> contentMap = Maps.newHashMap();
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			Pipeline pipe = jedis.pipelined();
			Map<String, byte[]> byteMap = new HashMap<String, byte[]>();
			Map<String, Response<byte[]>> responses = new HashMap<String, Response<byte[]>>();
			for (String key : keys) {
				responses.put(key, pipe.get((keyName + Constants.SEPERATOR_FLAG + key).getBytes()));
			}
			pipe.sync();

			Iterator<Entry<String, Response<byte[]>>> iterator = responses.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				byteMap.put((String) entry.getKey(), (byte[]) entry.getValue());
			}
			ByteArrayInputStream bis = null;
			ObjectInputStream inputStream = null;

			Iterator<Entry<String, byte[]>> iter = byteMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (null == entry.getKey()) {
					contentMap.put((String) entry.getKey(), null);
				} else {
					bis = new ByteArrayInputStream((byte[]) entry.getValue());
					inputStream = new ObjectInputStream(bis);
					Object readObject = (Object) inputStream.readObject();
					contentMap.put((String) entry.getKey(), readObject);
				}
			}
			if (null != inputStream) {
				inputStream.close();
			}
			if (null != bis) {
				bis.close();
			}

			return contentMap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 根据主键批量获取数据
	 * 
	 * @param keys
	 * @return
	 */
	@Override
	public List<Object> pipeGetList(List<String> keys) {
		List<Object> listContent = new ArrayList<Object>();
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			Pipeline pipe = jedis.pipelined();
			List<byte[]> byteArrays = new ArrayList<>();
			Map<String, Response<byte[]>> responses = new HashMap<String, Response<byte[]>>();
			for (String key : keys) {
				responses.put(key, pipe.get(key.getBytes()));
			}
			pipe.sync();

			for (String k : responses.keySet()) {
				byteArrays.add(responses.get(k).get());
			}
			ByteArrayInputStream bis = null;
			ObjectInputStream inputStream = null;
			for (byte[] byteArray : byteArrays) {
				if (null == byteArray) {
				} else {
					bis = new ByteArrayInputStream(byteArray);
					inputStream = new ObjectInputStream(bis);
					Object readObject = (Object) inputStream.readObject();
					listContent.add(readObject);
				}
			}
			if (null != inputStream) {
				inputStream.close();
			}
			if (null != bis) {
				bis.close();
			}
			return listContent;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.returnResource(jedis);
		}
		return null;
	}

	@Override
	public void saddObj(String key, Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			byte[] byteArray = bos.toByteArray();
			oos.close();
			bos.close();
			this.sadd(key.getBytes(), byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sadd(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.sadd(key, value);
		} finally {
			this.returnResource(jedis);
		}

	}

	@Override
	public List<Object> smembersObj(String key) {
		try {
			Set<byte[]> resultSet = this.smembers(key.getBytes());
			if (null == resultSet) {
				return null;
			}
			List<Object> resultList = new ArrayList<>();
			ByteArrayInputStream bis = null;
			ObjectInputStream inputStream = null;
			Iterator<byte[]> ite = resultSet.iterator();
			while (ite.hasNext()) {
				bis = new ByteArrayInputStream(ite.next());
				inputStream = new ObjectInputStream(bis);
				Object readObject = (Object) inputStream.readObject();
				resultList.add(readObject);
			}
			if (null != inputStream) {
				inputStream.close();
			}
			if (null != bis) {
				bis.close();
			}

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.smembers(key);
		} finally {
			this.returnResource(jedis);
		}

	}

	@Override
	public void sremObj(String key, Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			byte[] byteArray = bos.toByteArray();
			oos.close();
			bos.close();
			this.srem(key.getBytes(), byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void srem(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.srem(key, value);
		} finally {
			this.returnResource(jedis);
		}

	}
}
