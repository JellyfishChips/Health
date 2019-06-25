package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CheckitemGroupDao {
    List<CheckItem> findAll();

    void addGroup(CheckGroup checkGroup);

    void setCheckGroupAndCheckitem(Map<String, Integer> map);

    Page<CheckItem> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemIdsByCheckitemGroupId(Integer id);

    void update(CheckGroup checkGroup);

    void deleteAssocation(Integer id);
}
