package com.easy.datasource.bean;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

/**
 * DTO父类
 * <p>
 * 2021/5/19 11:22
 *
 * @author muchi
 */
@ToString
@Data
public class PageDTO {

    /**
     * 页码
     */
    @Schema(title = "页码", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long current;

    /**
     * 每页数量
     */
    @Schema(title = "每页数量", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long size;

    /**
     * 关键词
     */
    @Schema(title = "关键词")
    private String keyword;

    /**
     * 排序 传入排序字段以及正序倒序
     */
    @Schema(title = "排序")
    private List<OrderItem> orders;

    public Long getCurrent() {
        return Objects.isNull(current) ? 1 : current;
    }

    public Long getSize() {
        return Objects.isNull(size) ? 10 : size;
    }
}