package com.ofd.kafka.consumer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRunner<T> implements Runnable {
	
    private KafkaStream<byte[], byte[]> m_stream;
    private int m_threadNumber;
    
    private KafkaConsumeFactory<T> consumeFactory ;
    
    public KafkaConsumerRunner(){
    	
    }
    
    public KafkaConsumerRunner(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber, KafkaConsumeFactory<T> consumeFactory) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
        this.consumeFactory = consumeFactory;
    }
 
    @SuppressWarnings("unchecked")
    @Override
	public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        
        while (it.hasNext()){
        	
        	MessageAndMetadata<byte[], byte[]> msg = it.next();
        	byte[] streamByte = msg.message();
        	long offset = msg.offset();
        	
            System.out.println("Thread " + m_threadNumber + ": " + new String(streamByte));
            
            ByteArrayInputStream bis = new ByteArrayInputStream(streamByte);

            if(m_threadNumber>=1){
            	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
			try(ObjectInput in = new ObjectInputStream(bis)){
				
				consumeFactory.consume((T)in.readObject(), offset);
				
			} catch (Exception e) {
				System.out.println("Thread " + m_threadNumber + ": error ! " + e.toString());
			} 
        }
        
        System.out.println("Shutting down Thread: " + m_threadNumber);
        
    }

}
