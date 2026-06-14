package com.patrol.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求封装
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码（从 1 开始） */
    private long pageNum = 1;

    /** 每页条数 */
    private long pageSize = 10;

    /** 排序字段 */
    private String orderBy;

    /** 排序方向：ASC / DESC */
    private String orderDirection;
}
