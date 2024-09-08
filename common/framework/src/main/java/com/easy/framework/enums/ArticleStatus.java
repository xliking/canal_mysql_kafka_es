package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum ArticleStatus {

    UNREAD(0, "未读"),
    READ(1, "已读"),
    DRAFT(9, "未发布"),
    ;

    @EnumValue
    private final Integer value;
    private final String desc;


}