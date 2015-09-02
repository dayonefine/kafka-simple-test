package com.ofd.kafka.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
@Component
public class KafkaConsumerTemplate {
 
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    private ConsumerConnector consumerConnector;
	private String topic;
    private ExecutorService executor;
    private KafkaConsumeFactory<?> consumeFactory;
    
    public KafkaConsumerTemplate(){
    	
    }
    
    public void shutdown() {
        if (consumerConnector != null) consumerConnector.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
            	logger.debug("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
        	logger.debug("Interrupted during shutdown, exiting uncleanly");
        }
   }
 
    public void run(int a_numThreads) {
    	
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        
        executor = Executors.newFixedThreadPool(a_numThreads);
 
        int threadNumber = 0;
        
    	for (final KafkaStream<byte[], byte[]> stream : streams) {
    		executor.submit(new KafkaConsumerRunner<>(stream, threadNumber, consumeFactory));
    		threadNumber++;
    	}
		
    }

	public void setConsumerConnector(ConsumerConnector consumerConnector) {
		this.consumerConnector = consumerConnector;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public void setConsumeFactory(KafkaConsumeFactory<?> consumeFactory) {
		this.consumeFactory = consumeFactory;
	}

    
 
    
   
}
