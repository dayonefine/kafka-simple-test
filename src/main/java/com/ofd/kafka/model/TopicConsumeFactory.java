package com.ofd.kafka.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ofd.kafka.consumer.KafkaConsumeFactory;

public class TopicConsumeFactory implements KafkaConsumeFactory<Topic>{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void consume(Topic topic, long offset) throws Exception {
		logger.debug("====================================================");
		logger.debug("topic : " + topic.toString() + "| offset : " + offset);
		logger.debug("====================================================");
	}

}
