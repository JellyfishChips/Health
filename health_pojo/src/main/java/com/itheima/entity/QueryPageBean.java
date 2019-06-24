package com.itheima.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className QueryPageBean
 * @description
 * @return
 * @date 2019/6/22 20:12
 */
@Data
public class QueryPageBean implements Serializable {

    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件
}
