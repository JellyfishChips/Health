package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckitemGroupService {
    void addGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemIdsByCheckitemGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);


    List<CheckGroup> findAll();
}
