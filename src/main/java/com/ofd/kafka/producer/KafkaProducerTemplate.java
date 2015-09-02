package com.ofd.kafka.producer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KafkaProducerTemplate<T> {
	
	Producer<byte[], byte[]> producer;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private int count;
	
	public KafkaProducerTemplate(){}
	
	public void sendMsg(T object, String topic) throws IOException{
		
		KeyedMessage<byte[], byte[]> data = new KeyedMessage<>(topic, serialize(count),serialize(object));
		producer.send(data);
		
		count++;
		logger.debug(" sendMsg : " +count);
		
	}
	
	public void sendMsg(List<T> objectList, String topic) throws IOException{
		
		List<KeyedMessage<byte[], byte[]>> messageList = new ArrayList<>();
		
		for (T object : objectList) {
			
			KeyedMessage<byte[], byte[]> data = new KeyedMessage<>(topic, serialize(count), serialize(object));
			messageList.add(data);
		}
		producer.send(messageList);
		
		count++;
		logger.debug(" sendMsg : " +count);
		
	}
	
	public byte[] serialize(Object obj) throws IOException{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
	    	oos.writeObject(obj);
		} catch (IOException e) {
			throw e;
		}
	    return baos.toByteArray();
    }
	
	public void setProducer(Producer<byte[], byte[]> producer) {
		this.producer = producer;
	}
	
	public void shutdown(){
		producer.close();
	}


}
