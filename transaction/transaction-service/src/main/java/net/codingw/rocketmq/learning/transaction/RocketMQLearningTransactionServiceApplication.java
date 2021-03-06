package net.codingw.rocketmq.learning.transaction;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = {"net.codingw.rocketmq.learning.transaction.user.mapper" })
public class RocketMQLearningTransactionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQLearningTransactionServiceApplication.class, args);
    }
}
