# kafka-simple-test
## kafka producer
####applicationContext-kafka-producer.xml

	<bean id="kafkaProducer" class="com.ofd.kafka.producer.KafkaProducerTemplate" >
		<property name="producer" ref="producer"/>
	</bean>
	
	<bean id="producer" class="kafka.javaapi.producer.Producer">
		<constructor-arg ref="producerConfig"/>
	</bean>
	
	<bean id="producerConfig" class="kafka.producer.ProducerConfig">
		<constructor-arg>
			<props>
				<prop key="metadata.broker.list">10.32.14.20:9092,10.32.14.21:9092,10.32.14.22:9092</prop>
				<prop key="serializer.class">kafka.serializer.DefaultEncoder</prop>
				<!-- <prop key="serializer.class">kafka.serializer.DefaultEncoder</prop> -->
				<prop key="request.required.acks">1</prop>
				<prop key="partitioner.class">com.ofd.kafka.producer.SimplePartitioner</prop>
			</props>
		</constructor-arg>
	</bean>

####sendMag
kafkaProducer.sendMsg(topic, "feed")

## kafka consumer
####applicationContext-kafka-consumer.xml
	<bean id="topicConsumer" class="com.ofd.kafka.consumer.KafkaConsumerTemplate" destroy-method="shutdown">
		<property name="topic" value="feed"/>
		<property name="consumerConnector" ref="topicConsumerConnectorService" />
		<property name="consumeFactory" ref="consumeFactory" />
	</bean>
	
	<bean id="consumeFactory" class="com.ofd.kafka.model.TopicConsumeFactory"/>
			
	<bean id="topicConsumerConnectorService" class="kafka.consumer.Consumer"  factory-method="createJavaConsumerConnector" >
		<constructor-arg ref="topicConsumerConfig"/>
	</bean>
	
	<bean id="topicConsumerConfig" class="kafka.consumer.ConsumerConfig">
		<constructor-arg>
			<props>
				<prop key="zookeeper.connect">10.32.14.20:2181,10.32.14.21:2181,10.32.14.22:2181</prop>
			     <prop key="group.id">group1</prop>
			     <prop key="zookeeper.session.timeout.ms">400</prop>
			     <prop key="zookeeper.sync.time.ms">200</prop>
			     <!-- <prop key="auto.commit.enable">false</prop> -->
			     <prop key="auto.commit.interval.ms">1000</prop>
			     
			</props>
		</constructor-arg>
	</bean>

####TopicConsumeFactory

	public class TopicConsumeFactory implements KafkaConsumeFactory<Topic>{
	
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		@Override
		public void consume(Topic topic, long offset) throws Exception {
			logger.debug("====================================================");
			logger.debug("topic : " + topic.toString() + "| offset : " + offset);
			logger.debug("====================================================");
		}

	}
####Run consumer thread
	KafkaConsumerTemplate feedConsumer = context.getBean("feedConsumer", KafkaConsumerTemplate.class);
	try {
    		feedConsumer.run(4);
	} catch (Exception e) {
		System.out.println("[kafkaCunsumer.run error] " + e.toString());
		e.printStackTrace();
	}
	
	
