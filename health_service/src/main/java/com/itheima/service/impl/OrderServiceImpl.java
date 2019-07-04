package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.Constant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className OrderServiceImpl
 * @description
 * @return
 * @date 2019/6/30 16:46
 */
@Service( interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public Result submit(Map map) {
        String telephone = (String) map.get("telephone");
        int setmealId = Integer.parseInt(map.get("setmealId").toString());

        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = map.get("orderDate").toString();

        Date date = null;
        try {
            date = DateUtils.parseString2Date(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OrderSetting orderSetting = null;

        try {
            //从数据库进行查询是否为空
            orderSetting = orderSettingDao.findByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //进行判断是否为空
        if (orderSetting == null) {
            return new Result(false, Constant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (number <= reservations) {
            return new Result(false, Constant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约

        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            //检查当前账户是否为会员
            //如果是会员 ， 拿到他的member_id,和orderDate，setmeal_id去预约表查询，确认是否重复预约
            Integer memberId = member.getId();
            Order order = new Order();
            try {
                order.setOrderDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            order.setId(memberId);
            order.setSetmealId(setmealId);

            //拿到order进行查询
            List<Order> condition = orderDao.findByCondition(order);

            //判断如果已存在，返回false
            if (condition != null || condition.size() > 0) {
                return new Result(false, Constant.HAS_ORDERED);
            }

        } else {

            //不是会员， 将数据添加到会员列表中
            member = new Member();
            member.setIdCard(map.get("idCard").toString());
            member.setName(map.get("name").toString());
            member.setSex(map.get("sex").toString());
            member.setPhoneNumber(map.get("telephone").toString());
            memberDao.add(member);
        }

        //进行预约
        Order orderType = new Order(member.getId(), date, map.get("orderType").toString(), Order.ORDERSTATUS_NO, setmealId);
        orderDao.add(orderType);

        //预约成功，更新当日预约人数
        orderSetting.setReservations(reservations + 1);
        orderSettingDao.editNumberByOrderDate(orderSetting);

        //返回true
        return new Result(true, Constant.ORDER_SUCCESS, orderType);
    }

    /**
     * @className OrderServiceImpl
     * @description  查询套餐详情
     * @param id
     * @return java.util.Map
     * @author JellyfishChips
     * @date 2019/6/30 22:53
     * @version 1.0
     */
    @Override
    public Map findById(String id) {
        return orderDao.findById4Detail(Integer.parseInt(id));
    }
}
