package com.changhf.service.rocketmq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;

/**
 * PullConsumer，订阅消息
 * 
 */
@Service("mqconsumer")
public class PullConsumer {
	private DefaultMQPullConsumer consumer;
	// Java缓存
	private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();

	@PostConstruct
	public  void init() throws MQClientException  {
		consumer = new DefaultMQPullConsumer("PullConsumerGroup");
		consumer.setNamesrvAddr("172.23.21.221:9876;172.23.21.222:9876");
		consumer.start();
	}
	public void consume(String mobile)throws MQClientException{
		// 获取订阅topic的队列，默认队列大小是4
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("MyTopic");
		for (MessageQueue mq : mqs) {
//			System.out.println("Consume from the queue: " + mq);
			SINGLE_MQ: while (true) {
				try {//阻塞的拉去消息，中止时间默认30s
					PullResult pullResult = consumer.pull(mq, null, getMessageQueueOffset(mq), 32);
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch (pullResult.getPullStatus()) {
					case FOUND://pullSataus  
						List<MessageExt> list = pullResult.getMsgFoundList();
						for(MessageExt msg:list){
							if(msg.getKeys()!=null && mobile.equals(msg.getKeys())){
								System.out.println(msg.getMsgId()+"----"+new String(msg.getBody()));
								break;
							}
						}
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						break SINGLE_MQ;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@PreDestroy
    public void destroy() {
		consumer.shutdown();
    }
	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
		offseTable.put(mq, offset);
	}

	private static long getMessageQueueOffset(MessageQueue mq) {
		Long offset = offseTable.get(mq);
		if (offset != null) {
//			System.out.println(offset);
			return offset;
		}
		return 0;
	}
}