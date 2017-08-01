package com.changhf.service.rocketmq;

import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
//@Service("mqconsumer")
public class MqConsumer {
    private DefaultMQPushConsumer defaultMQPushConsumer;
	
	@Value("${namesrvAddr}")
    private String namesrvAddr;
    @Value("${consumerGroup}")
    private String consumerGroup;
    
//    @PostConstruct
//    public void init(){
//    	
//    }
    
    public void consume(String tag) throws MQClientException {
    	// 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
    	// 注意：ConsumerGroupName需要由应用来保证唯一
    	defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
    	defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
    	defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
    	defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    	// 设置为集群消费(区别于广播消费)
    	defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
    	// 订阅指定MyTopic下tags等于MyTag
    	defaultMQPushConsumer.subscribe("MyTopic", tag);
    	
    	defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
    		
    		// 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
    		@Override
    		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
    			
    			MessageExt msg = msgs.get(0);
    			if (msg.getTopic().equals("MyTopic")) {
    				// TODO 执行Topic的消费逻辑
    				if (msg.getTags() != null && msg.getTags().equals(tag)) {
    					// TODO 执行Tag的消费
    					System.out.println(new String(msg.getBody()));
    				}
    			}
    			// 如果没有return success ，consumer会重新消费该消息，直到return success
    			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    		}
    	});
    	// Consumer对象在使用之前必须要调用start初始化，初始化一次即可
    	defaultMQPushConsumer.start();
    	
    }
    @PreDestroy
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }
}