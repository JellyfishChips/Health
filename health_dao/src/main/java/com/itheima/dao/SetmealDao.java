package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {
    Page<Setmeal> findAll(String queryString);

    void add(Setmeal setmeal);

    void setSetmealAndCheckGroupIds(Map<String, Integer> map);
}
