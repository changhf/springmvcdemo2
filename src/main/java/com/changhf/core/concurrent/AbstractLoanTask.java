package com.changhf.core.concurrent;

import org.apache.log4j.Logger;

public class AbstractLoanTask implements LoanTask {
	private Logger logger = Logger.getLogger(AbstractLoanTask.class);
	public Thread task = new Thread("Task") {

		private boolean isRun = true;

		@Override
		public void run() {
			while (isRun) {
				logger.debug(this.getName() + " Running!");
				doLoan();//执行业务
				doWait();//释放对象锁
			}
		}

		public void stopThread() {
			isRun = false;
		}

	};

	public AbstractLoanTask() {
		task.start();
		logger.debug("线程启动！");
	}

	@Override
	public void execute() {
		logger.debug("task.execute()");
		Object lock = getLock();
		System.out.println("------------2222222--------");
		synchronized (lock) {
			lock.notifyAll();//唤醒所有等待在obj上的线程
		}
	}

	@Override
	public void doLoan() {
		logger.debug("AbstractTask开始");
	}

	public void doWait() {
		Object lock = this.getLock();
		synchronized (getLock()) {
			try {
				System.out.println("-------waiting-------");
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {

	}

	@Override
	public Object getLock() {
		return LoanTask.LOAN_STATUS;
	}

}
