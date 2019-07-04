package com.itheima.controller;

import com.itheima.constant.Constant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className UserController
 * @description
 * @return
 * @date 2019/7/4 9:41
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, Constant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.GET_USERNAME_FAIL);
        }
    }
}
