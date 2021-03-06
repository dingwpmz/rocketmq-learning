package net.codingw.rocketmq.learning.transaction.common.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(MqProducerProperties.class)
public class MQConfig {

    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    @Value("${apache.rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Bean
    public DefaultMQProducer defaultMQProducer() {
        //生产者的组名
        DefaultMQProducer producer= new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        } catch (MQClientException e) {
           throw new RuntimeException("mq 配置错误", e);
        }

        return producer;
    }

    @Bean
    public TransactionMQProducerContainer transactionMQProducerContainer() {
        return new TransactionMQProducerContainer();
    }

}
