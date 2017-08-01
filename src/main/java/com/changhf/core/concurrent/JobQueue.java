package com.changhf.core.concurrent;


import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.changhf.domain.ValueEvent;

public class JobQueue<T> {
	private Queue<T> queue = new ConcurrentLinkedQueue<T>();
	private Lock LOCK=new ReentrantLock();
	private LoanTask task;
	
    private static class LazyHolder {
        // 用户相关队列
        private static final JobQueue <ValueEvent> USER_INSTANCE = new JobQueue<ValueEvent>(new UserTask());
    }

    /**
     * 用户相关处理：注册开户，绑定银行卡等
     * @return
     */
    public static JobQueue <ValueEvent> getUserInstance() {
        return LazyHolder.USER_INSTANCE;
    }
    
    private JobQueue(LoanTask task){
		super();
		this.task = task;
    }

	public void offer(T model) {
		if (!queue.contains(model)) {
			queue.offer(model);
			try {
				
				System.out.println("------------11111111--------");
				LOCK.lock();
				task.execute();//唤醒所有等待在obj上的线程
				System.out.println("------------33333333--------");
			}finally{
				LOCK.unlock();
				System.out.println("-----44444444444-------");
			}
		}
	}

	public  void offer(List<T> ts) {
		for (int i = 0; i < ts.size(); i++) {
			T t = ts.get(i);
			if (!queue.contains(t)) {
				queue.offer(t);
			}
		}
		try {
			LOCK.lock();
			task.execute();
		}finally{
			LOCK.unlock();
		}
	}

	public  T poll() {
		return queue.poll();
	}

	public  T peek() {
		return queue.peek();
	}

	public  void remove(T model) {
		queue.remove(model);
	}

	public int size() {
		return queue.size();
	}
	public  boolean isEmpty() {
		try {
			LOCK.lock();
			return queue.isEmpty();
		} finally {
			LOCK.unlock();
		}
		
	}

	public  void stop() {
		task.stop();
	}
	



}
