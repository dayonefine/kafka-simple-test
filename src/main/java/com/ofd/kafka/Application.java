package com.ofd.kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ofd.kafka.consumer.KafkaConsumerTemplate;
import com.ofd.kafka.model.Topic;
import com.ofd.kafka.producer.KafkaProducerTemplate;

public class Application {
	
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
    	
    	@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	
    	KafkaConsumerTemplate feedConsumer = context.getBean("feedConsumer", KafkaConsumerTemplate.class);
    	KafkaProducerTemplate<Topic> kafkaProducer = context.getBean("kafkaProducer", KafkaProducerTemplate.class);
    	
    	try {
    		Topic topic = new Topic();
    		topic.setDesc("desc_" + System.currentTimeMillis());
    		topic.setName("name_" + System.currentTimeMillis());
    		kafkaProducer.sendMsg(topic, "feed");
    		
    		feedConsumer.run(4);
			
    		
			
		} catch (Exception e) {
			System.out.println("[kafkaCunsumer.run error] " + e.toString());
			e.printStackTrace();
		}

    }
}
