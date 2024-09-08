package com.easy.framework.bean.base;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 公共父类VO id
 * </p>
 *
 * @author muchi
 */
@Data
@Tag(name = "公共父类VO id")
public class BaseIdVO {

    @Schema(description = "主键ID")
    private String id;
}