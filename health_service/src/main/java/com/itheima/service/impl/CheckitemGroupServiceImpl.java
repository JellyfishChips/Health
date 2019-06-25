package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckitemGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckitemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className CheckitemGroupServiceImpl
 * @description
 * @return
 * @date 2019/6/24 21:23
 */
@Service(interfaceClass = CheckitemGroupService.class)
@Transactional
public class CheckitemGroupServiceImpl implements CheckitemGroupService {

    @Autowired
    private CheckitemGroupDao checkitemGroupDao;

    //新增检查组的同时，需要设置检查组和检查项的关联关系
    @Override
    public void addGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkitemGroupDao.addGroup(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkitemGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @className CheckitemGroupServiceImpl
     * @description   根据id查询
     * @param id
     * @return com.itheima.pojo.CheckGroup
     * @author JellyfishChips
     * @date 2019/6/25 9:59
     * @version 1.0
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkitemGroupDao.findById(id);
    }


    //根据检查组id查询检查项id
    @Override
    public List<Integer> findCheckitemIdsByCheckitemGroupId(Integer id) {
        return checkitemGroupDao.findCheckitemIdsByCheckitemGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更改基本信息
        checkitemGroupDao.update(checkGroup);
        //首先删除中间表关系的id
        checkitemGroupDao.deleteAssocation(checkGroup.getId());
        //添加中间表的关系
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkGroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkitemGroupDao.setCheckGroupAndCheckitem(map);
            }
        }
    }
}
