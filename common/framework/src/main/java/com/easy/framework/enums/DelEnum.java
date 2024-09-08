package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.easy.utils.enums.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DelEnum implements EnumInterface<Integer> {

    /**
     * 启用
     */
    ENABLE(0, "启用"),
    DELETE(1, "删除");

    @EnumValue
    private final Integer code;
    private final String introduction;
}