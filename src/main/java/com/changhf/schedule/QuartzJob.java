package com.changhf.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableScheduling
public class QuartzJob {
	private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	// @Scheduled(cron = "0 */5 * * * ?")//每小时执行一次
	// @Scheduled(cron = "0 */30 * * * ?")//每隔30分
	// @ Scheduled(cron = "*/20 * * * * ?")//每20分钟执行一次
	// @Scheduled(cron = "0 0 7 * * ?")//每天凌晨7点整
	// @Scheduled(cron = "0 30 14 * * ?")//每天14:30
    
    /**
     * 信托到期前5天时发送短信通知到指定用户
     */
    @Scheduled(cron = "0 30 10 * * ?")//每天早上10点
    public void printCurrentTime(){
        logger.info("定时器printCurrentTime------------start");
        System.out.println(new Date());
        logger.info("定时器printCurrentTime------------end");
    }
}
