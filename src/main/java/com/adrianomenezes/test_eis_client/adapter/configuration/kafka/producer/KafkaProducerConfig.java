package com.adrianomenezes.test_eis_client.adapter.configuration.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	String bootstrapAddress;

	@Value("${spring.kafka.consumer.max.retry.attempt}")
	int maxRetryAttempt;

	@Value("${spring.kafka.consumer.retry.await.time}")
	int retryAwaitTime;

	@Bean
	public KafkaProducer<String,String> producerFactory() {
		return new KafkaProducer<String,String>(producerConfigs());
	}

	@Bean
	public ProducerFactory<String, String> producerDefaultFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

		return props;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerDefaultFactory());
	}


}
