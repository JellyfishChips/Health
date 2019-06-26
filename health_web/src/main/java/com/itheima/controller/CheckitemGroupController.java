package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.Constant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckitemGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className CheckitemGroupController
 * @description
 * @return
 * @date 2019/6/24 21:15
 */
@RestController
@RequestMapping("/checkitemGroup")
public class CheckitemGroupController {

    @Reference
    private CheckitemGroupService checkitemGroupService;

    @RequestMapping("/addGroup")
    public Result addGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkitemGroupService.addGroup(checkGroup, checkitemIds);
            return new Result(true, Constant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, Constant.ADD_CHECKGROUP_FAIL);
        }
    }

    //分页
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkitemGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
    }

    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkitemGroupService.findById(id);
            if (checkGroup != null) {
                return new Result(true, Constant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
            } else {
                return new Result(false, Constant.QUERY_CHECKGROUP_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //根据检查组合id查询对应的所有检查项id
    @RequestMapping("/findCheckitemIdsByCheckitemGroupId")
    public Result findCheckitemIdsByCheckitemGroupId(Integer id) {
        return new Result(true, Constant.QUERY_CHECKGROUP_SUCCESS, checkitemGroupService.findCheckitemIdsByCheckitemGroupId(id));

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkitemGroupService.edit(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, Constant.EDIT_CHECKGROUP_SUCCESS);
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> groups = checkitemGroupService.findAll();
            return new Result(true, Constant.QUERY_CHECKGROUP_SUCCESS, groups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.QUERY_CHECKGROUP_FAIL );
        }

    }
}
