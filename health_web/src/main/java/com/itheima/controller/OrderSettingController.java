package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.Constant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className OrderSettingController
 * @description
 * @return
 * @date 2019/6/27 16:13
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * @className OrderSettingController
     * @description  上传xlsx文件
     * @param multipartFile
     * @return com.itheima.entity.Result
     * @author JellyfishChips
     * @date 2019/6/27 17:34
     * @version 1.0
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile) {
        System.out.println(multipartFile.getOriginalFilename());
        try {
            //读取数据文件
            List<String[]> list = POIUtils.readExcel(multipartFile);
            List<OrderSetting> orderLists = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (String[] strings : list) {
                    //将解析出来的数据添加到实体类中
                    orderLists.add(new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1])));
                }
                orderSettingService.add(orderLists);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.UPLOAD_FAIL);
        }
        return new Result(true, Constant.UPLOAD_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String Date) {
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(Date);
            return new Result(true, Constant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, Constant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.ORDERSETTING_FAIL);
        }
    }
}
