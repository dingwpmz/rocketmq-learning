package net.codingw.rocketmq.learning.transaction.gateway.control;


import com.alibaba.dubbo.config.annotation.Reference;
import net.codingw.rocketmq.learning.transaction.api.user.dto.UserDto;
import net.codingw.rocketmq.learning.transaction.api.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserControl {

    @Reference(check = false)
    private UserService userServiceProxy;


    @RequestMapping("/reg")
    @ResponseBody
    public Map<String, Object> reg(@RequestParam("username") String username,
                                   @RequestParam("pwd") String password,
                                   @RequestParam("email") String email,
                                   @RequestParam("sex") int sex){

        Map<String, Object> result = new HashMap<String, Object>();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.put("code", 1);
            result.put("msg", "用户名与密码不能为空");
            return result;
        }

        try {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setSex(sex);
            userDto.setUserName(username);
            return userServiceProxy.reg(userDto);
        } catch (Throwable e ) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg" , e.getMessage());
        }

        return result;
    }






}
