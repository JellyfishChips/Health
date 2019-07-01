package com.itheima.constant;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className RedisMessageConstant
 * @description
 * @return
 * @date 2019/6/28 20:14
 */
public class RedisMessageConstant {

    public static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
