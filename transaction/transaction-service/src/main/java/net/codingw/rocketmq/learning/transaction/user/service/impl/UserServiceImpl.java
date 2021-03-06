package net.codingw.rocketmq.learning.transaction.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import net.codingw.rocketmq.learning.transaction.api.user.dto.UserDto;
import net.codingw.rocketmq.learning.transaction.api.user.service.UserService;
import net.codingw.rocketmq.learning.transaction.common.config.TransactionMQProducerContainer;
import net.codingw.rocketmq.learning.transaction.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionMQProducerContainer transactionMQProducerContainer;

    @Autowired
    private UserRegTransactionListener userLoginTransactionListener;


    public Map<String, Object> reg(UserDto userDto) {
        Map result = new HashMap<>();
        try {
            // 校验参数
            checkParam(userDto);
            // 事务消息发散，为了方便对消息进行查找，建议对消息加上 key
            TransactionSendResult sendResult = transactionMQProducerContainer.getTransactionMQProducer("user_reg_topic", userLoginTransactionListener)
                    .sendMessageInTransaction(new Message("user_reg_topic",null, userDto.getUserName(), JSON.toJSONString(userDto).getBytes()),null);

            // 事务消息发送成功，返回注册成功，因为是事务消息发送接口是同步调用纺纱，该方法调用结束后，可以根据对状态进行判断业务是否成功
            // userRegTransactionListener 的 executeocalTransaction 执行本地方法已经结束
            if(sendResult.getSendStatus().equals(SendStatus.SEND_OK) &&
                !sendResult.getLocalTransactionState().equals(LocalTransactionState.ROLLBACK_MESSAGE)) {
                result.put("code", 0);
            } else {
                result.put("code", 1);
                result.put("msg", sendResult.getLocalTransactionState().equals(LocalTransactionState.ROLLBACK_MESSAGE) ? "业务异常" : "系统繁忙");
            }

        } catch (Throwable e) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    private static void checkParam(UserDto userDto) {
        if(StringUtils.isEmpty(userDto.getUserName()) ||
            StringUtils.isEmpty(userDto.getPassword())) {
            throw new RuntimeException("非法参数");
        }
    }

}
