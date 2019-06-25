package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;


public interface CheckitemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    Long findCountByCheckitemId(Integer id);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

}
