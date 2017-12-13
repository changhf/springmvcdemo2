package com.changhf.service.rocketmq;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CacheService {
    static Cache<Integer, String> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(5, TimeUnit.SECONDS)
			.build();
	
	public static void main(String[] args) throws Exception {
		new Thread() { //monitor
			public void run() {
				while(true) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					System.out.println(sdf.format(new Date()) +" size: "+cache.size());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		cache.put(1, "Hi");
		System.out.println("write key:1 ,value:"+cache.getIfPresent(1));
		Thread.sleep(10000);
		// when write ,key:1 clear
		cache.put(2, "bbb");
		System.out.println("write key:2 ,value:"+cache.getIfPresent(2));
		Thread.sleep(10000);
		// when read other key ,key:2 do not clear
		System.out.println(sdf.format(new Date())
				+" after write, key:1 ,value:"+cache.getIfPresent(1));
		Thread.sleep(2000);
		// when read same key ,key:2 clear
		System.out.println(sdf.format(new Date())
				+" final, key:2 ,value:"+cache.getIfPresent(2));
	}
}