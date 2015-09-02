package com.ofd.kafka.producer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePartitioner implements Partitioner {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    public SimplePartitioner (VerifiableProperties props) {
    }
 
    @Override
    public int partition(Object keyObject, int a_numPartitions) {
    	
    	Integer key = (Integer)deSerialize((byte[]) keyObject);
    	
    	logger.debug("key :" + key + ", a_numPartitions : " +a_numPartitions);
    	return key % a_numPartitions;
    }
    
    public Object deSerialize(byte[] objectByte){
    	
    	Object RetObject=null;
    	ByteArrayInputStream bis = new ByteArrayInputStream(objectByte);
		try(ObjectInput in = new ObjectInputStream(bis)){
			RetObject = in.readObject();
		} catch (Exception e) {
			
		} 
		
		return RetObject;
    }
 
}