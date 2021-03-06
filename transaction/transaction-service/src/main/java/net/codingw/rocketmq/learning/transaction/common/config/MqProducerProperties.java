package net.codingw.rocketmq.learning.transaction.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "apache.rocketmq.producer")
public class MqProducerProperties {

    private String producerGroup;
    private String namesrvAddr;
    private String transactionProducerGroupPrefix;

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getTransactionProducerGroupPrefix() {
        return transactionProducerGroupPrefix;
    }

    public void setTransactionProducerGroupPrefix(String transactionProducerGroupPrefix) {
        this.transactionProducerGroupPrefix = transactionProducerGroupPrefix;
    }
}
