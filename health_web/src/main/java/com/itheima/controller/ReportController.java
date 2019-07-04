package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.Constant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className ReportController
 * @description
 * @return
 * @date 2019/7/4 10:20
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Map map = new HashMap<>();
        LocalDate localDate = LocalDate.now().minusMonths(12);
        List<String> months = new ArrayList<>();
        for (int i = 1; i <=12; i++) {

            LocalDate temp = localDate.plusMonths(i);
            months.add(temp.toString().substring(0,7));
        }

        map.put("months", months);
        List<Integer> memberCounts = memberService.findByCountOfMonths(months);
        map.put("memberCount", memberCounts);
        return new Result(true, Constant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now().minusMonths(12);
        List<String> list = new ArrayList<>();
        for (int i = 1; i <=12; i++) {

            LocalDate temp = localDate.plusMonths(i);
            list.add(temp.toString().substring(0,7));
        }

        list.forEach(item->{
            System.out.println(item);
        });

    }
}
