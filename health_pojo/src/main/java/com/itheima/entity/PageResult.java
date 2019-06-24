package com.itheima.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className PageResult
 * @description 分页结果封装对象
 * @return
 * @date 2019/6/22 20:10
 */
@Data
public class PageResult implements Serializable {

    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
