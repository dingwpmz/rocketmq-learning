package net.codingw.rocketmq.learning.transaction.common.config;


import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class TransactionMQProducerContainer {
    private Map<String, TransactionMQProducer> containers = new HashMap<>();

    /** 生产者的组名*/
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;
    @Value("${apache.rocketmq.producer.transactionProducerGroupPrefix}")
    private String transactionProducerGroupPrefix;
    @Value("${apache.rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    public TransactionMQProducer getTransactionMQProducer(String topic, TransactionListener transactionListener) {
        TransactionMQProducer producer = containers.get(topic);
        if(producer == null ) {
            synchronized (TransactionMQProducerContainer.class) {
                producer = containers.get(topic);
                if(producer == null ) {
                    producer = new TransactionMQProducer(transactionProducerGroupPrefix+"-" + topic);
                    producer.setNamesrvAddr(namesrvAddr);
                    producer.setTransactionListener(transactionListener);
                    try {
                        producer.start();
                    }catch (Throwable e) {
                        e.printStackTrace();
                        throw new RuntimeException("生产者启动异常", e);
                    }
                    containers.put(topic, producer);
                    return producer;
                }
            }
        }
        return producer;
    }


}
