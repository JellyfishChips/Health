package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className OrderSettingServiceImpl
 * @description
 * @return
 * @date 2019/6/27 16:29
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * @className OrderSettingServiceImpl
     * @description 上传xlsx文件
     * @param orderLists
     * @return void
     * @author JellyfishChips
     * @date 2019/6/27 17:51
     * @version 1.0
     */
    @Override
    public void add(List<OrderSetting> orderLists) {
        if (orderLists != null && orderLists.size() > 0) {
            for (OrderSetting orderList : orderLists) {
                addOrderSetting(orderList);
            }
        }
    }

    /**
     * @className OrderSettingServiceImpl
     * @description  查询预约信息
     * @param date
     * @return java.util.List<java.util.Map>
     * @author JellyfishChips
     * @date 2019/6/27 17:52
     * @version 1.0
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        //传过来的参数格式是2019-3
        //后面需要加上日
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        Map map = new HashMap();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> maps = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Object> hashmap = new HashMap<>();
            hashmap.put("date", orderSetting.getOrderDate().getDate());
            hashmap.put("number", orderSetting.getNumber());
            hashmap.put("reservations", orderSetting.getReservations());
            maps.add(hashmap);
        }
        return maps;
    }

    /**
     * @className OrderSettingServiceImpl
     * @description   设置预约
     * @param orderSetting
     * @return void
     * @author JellyfishChips
     * @date 2019/6/28 10:30
     * @version 1.0
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        addOrderSetting(orderSetting);
    }

    private void addOrderSetting(OrderSetting orderList) {
        long count = orderSettingDao.findCountByOrderDate(orderList.getOrderDate());
        if (count > 0) {
            //存在进行更新操作
            orderSettingDao.editNumberByOrderDate(orderList);
        }
        if (count == 0) {
            //不存在进行添加操作
            orderSettingDao.add(orderList);
        }
    }
}
