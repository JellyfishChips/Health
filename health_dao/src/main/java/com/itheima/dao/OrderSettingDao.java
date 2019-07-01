package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderList);

    void add(OrderSetting orderList);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    OrderSetting findByDate(Date date);

    void update(OrderSetting orderSetting);
}
