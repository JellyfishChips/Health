package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submit(Map map);

    Map findById(String id);
}
