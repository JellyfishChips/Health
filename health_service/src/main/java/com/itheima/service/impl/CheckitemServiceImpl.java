package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.Constant.Constant;
import com.itheima.dao.CheckitemDao;
import com.itheima.dao.CheckitemGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className CheckitemServiceImpl
 * @description
 * @return
 * @date 2019/6/23 15:03
 */
@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {

    @Autowired
    private CheckitemDao checkitemDao;

    @Autowired
    private CheckitemGroupDao checkitemGroupDao;

    /**
     * @className CheckitemServiceImpl
     * @description  删除数据
     * @param id
     * @return void
     * @author JellyfishChips
     * @date 2019/6/23 19:21
     * @version 1.0
     */
    @Override
    public void delete(Integer id) {
        //判断检查项是否被引用
        Long count = checkitemDao.findCountByCheckitemId(id);
        if (count > 0) {
            //如果被引用，报错
            throw new RuntimeException(Constant.CHECKITEM_IS_CALLED);
        }
        //没有被引用，进行删除
        checkitemDao.delete(id);
    }

    /**
     * @className CheckitemServiceImpl
     * @description  根据id查询检查项
     * @param id
     * @return com.itheima.pojo.CheckItem
     * @author JellyfishChips
     * @date 2019/6/23 20:18
     * @version 1.0
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkitemDao.findById(id);
    }

    /**
     * @className CheckitemServiceImpl
     * @description 编辑检查项
     * @param checkItem
     * @return void
     * @author JellyfishChips
     * @date 2019/6/24 10:26
     * @version 1.0
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkitemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkitemGroupDao.findAll();
    }

    /**
     * @className CheckitemServiceImpl
     * @description  新增检查项
     * @param checkItem
     * @return void
     * @author JellyfishChips
     * @date 2019/6/23 15:07
     * @version 1.0
     */
    @Override
    public void add(CheckItem checkItem) {
        checkitemDao.add(checkItem);
    }

    /**
     * @className CheckitemServiceImpl
     * @description  分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return com.itheima.entity.PageResult
     * @author JellyfishChips
     * @date 2019/6/23 20:17
     * @version 1.0
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkitemDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
