# kafka-simple-test
## kafka producer
1. applicationContext-kafka-producer.xml
<code>
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
</code>
2. sendMag
<code>kafkaProducer.sendMsg(topic, "feed");</code>
