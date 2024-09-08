package com.easy.framework.bean.base;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页数据
 * </p>
 *
 * @author muchi
 */
@Data
@Tag(name = "分页数据")
public class PageVO<T> {
    /**
     * 查询数据列表
     */
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;

    public PageVO(long size, long current) {
        this.size = size;
        this.current = current;
    }
}