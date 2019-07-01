package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.Constant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className OrderController
 * @description
 * @return
 * @date 2019/6/30 16:10
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //校验验证码
        //获得验证码
        String validateCode = (String) map.get("validateCode");
        //获得手机号
        String telephone = (String) map.get("telephone");
        //获得redis中的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //进行校验
        if (codeInRedis == null || !codeInRedis.equalsIgnoreCase(validateCode)) {

            return new Result(false, Constant.VALIDATECODE_ERROR);
        }

        //调用服务新增预约
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = orderService.submit(map);

        //成功发送预约短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, map.get("orderDate").toString());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(String id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, Constant.QUERY_SETMEAL_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.QUERY_SETMEAL_FAIL);
        }
    }
}
