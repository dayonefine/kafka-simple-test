package com.ofd.kafka.producer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class SimpleEncoder<Object> implements Encoder<Object>{

	public SimpleEncoder(VerifiableProperties props) {
		
	}
	
	@Override
	public byte[] toBytes(Object object) {
		
//		if(object instanceof String){
//			
//		}else if(object instanceof Integer){
//			
//		}else {
//			return serialize(object);
//		}
		
		return serialize(object);
	}
	
	public byte[] serialize(Object obj) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
	    	oos.writeObject(obj);
		} catch (IOException e) {
			
		}
	    return baos.toByteArray();
    }
	
}