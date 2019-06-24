package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

public interface CheckitemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
}
