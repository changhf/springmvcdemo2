package com.changhf.schedule;

import com.changhf.utils.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
// @EnableScheduling
public class QuartzJob {
    @Autowired
    private DateConverter dateConverter;
	private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	// @Scheduled(cron = "0 */5 * * * ?")//每隔五分钟执行一次
	// @Scheduled(cron = "0 */30 * * * ?")//每隔30分
	// @ Scheduled(cron = "*/20 * * * * ?")//每20秒执行一次
	// @Scheduled(cron = "0 0 7 * * ?")//每天凌晨7点整
	// @Scheduled(cron = "0 30 14 * * ?")//每天14:30
    
    /**
     * 测试
     */
    @Scheduled(cron = "0 */2 * * * ?")//每隔两分钟
    public void printCurrentTime(){
        logger.info("定时器printCurrentTime------------start");
        System.out.println(dateConverter.format(new Date()));
        logger.info("定时器printCurrentTime------------end");
    }
}
