package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.Constant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className LoginController
 * @description  登录
 * @return
 * @date 2019/7/1 20:24
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        String telephone = String.valueOf(map.get("telephone"));

        String validateCode = String.valueOf(map.get("validateCode"));
        //获得redis中的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

        //校验验证码
        if (codeInRedis == null || !codeInRedis.equalsIgnoreCase(validateCode)) {
            return new Result(false, Constant.VALIDATECODE_ERROR);
        } else {
            //判断是否为会员
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
                //当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //登陆成功
            //写入cookie， 跟踪用户
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
            return new Result(true, Constant.LOGIN_SUCCESS);
        }
    }
}
