package com.ofd.kafka.consumer;


public interface KafkaConsumeFactory<T> {
	
	public void consume(T Object, long offset) throws Exception;
	

}
