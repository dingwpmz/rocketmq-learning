package net.codingw.rocketmq.learning.transaction.api.user.service;

import net.codingw.rocketmq.learning.transaction.api.user.dto.UserDto;

import java.util.Map;

public interface UserService {

    public Map<String, Object> reg(UserDto userDto);
}
