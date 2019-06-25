package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.Constant.Constant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckitemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className CheckitemController
 * @description 检查控制器
 * @return
 * @date 2019/6/23 13:12
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    @Reference
    private CheckitemService checkitemService;

    /**
     * @className CheckitemController
     * @description   新增检查项
     * @param checkItem
     * @return com.itheima.entity.Result
     * @author JellyfishChips
     * @date 2019/6/23 19:15
     * @version 1.0
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, Constant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * @className CheckitemController
     * @description   分页查询
     * @param queryPageBean
     * @return com.itheima.entity.PageResult
     * @author JellyfishChips
     * @date 2019/6/23 19:15
     * @version 1.0
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkitemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * @className CheckitemController
     * @description 删除数据
     * @param id
     * @return com.itheima.entity.Result
     * @author JellyfishChips
     * @date 2019/6/23 19:16
     * @version 1.0
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkitemService.delete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, Constant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * @className CheckitemController
     * @description  编辑页面回显数据查询
     * @param id
     * @return com.itheima.entity.Result
     * @author JellyfishChips
     * @date 2019/6/23 20:17
     * @version 1.0
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        CheckItem checkItem = new CheckItem();
        try {
            checkItem = checkitemService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.QUERY_CHECKGROUP_FAIL);
        }
        return new Result(true, Constant.QUERY_CHECKGROUP_SUCCESS, checkItem);
    }

    /**
     * @className CheckitemController
     * @description  编辑检查项
     * @param checkItem
     * @return com.itheima.entity.Result
     * @author JellyfishChips
     * @date 2019/6/24 10:24
     * @version 1.0
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.edit(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, Constant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> checkitemList = checkitemService.findAll();
            if (checkitemList != null && checkitemList.size() > 0) {
                return new Result(true, Constant.QUERY_CHECKGROUP_SUCCESS, checkitemList);
            } else {
                return new Result(false, Constant.QUERY_CHECKGROUP_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, Constant.ADD_CHECKGROUP_FAIL);
        }
    }
}
