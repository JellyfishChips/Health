package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className SetmealSerivceImpl
 * @description
 * @return
 * @date 2019/6/25 23:36
 */
@Service(interfaceClass = SetmealSerivce.class)
@Transactional
public class SetmealSerivceImpl implements SetmealSerivce {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    /**
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return com.itheima.entity.PageResult
     * @className SetmealSerivceImpl
     * @description 分页查询
     * @author JellyfishChips
     * @date 2019/6/25 23:36
     * @version 1.0
     */
    @Override
    public PageResult findAll(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findAll(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //新增
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //新增基本信息
        setmealDao.add(setmeal);
        //新增两表之间的关系
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            setSetmealAndCheckGroupIds(setmeal.getId(), checkgroupIds);
        }

        //将图片名保存到jedis
        setPicInRedis(setmeal.getImg());
    }

    private void setPicInRedis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    private void setSetmealAndCheckGroupIds(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkgroupId);
            setmealDao.setSetmealAndCheckGroupIds(map);
        }
    }
}
