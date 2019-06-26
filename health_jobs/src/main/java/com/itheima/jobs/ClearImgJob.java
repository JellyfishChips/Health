package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className ClearImgJob
 * @description
 * @return
 * @date 2019/6/26 20:58
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //清理图片
    public void clearImg() {
        //计算两个redis中的差值
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        //使用迭代器进行遍历删除
        Iterator<String> it = sdiff.iterator();
        while (it.hasNext()) {
            String pic = it.next();
            //删除图片服务器中的图片
            QiniuUtils.deleteFileFromQiniu(pic);
            //删除redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }
    }
}
