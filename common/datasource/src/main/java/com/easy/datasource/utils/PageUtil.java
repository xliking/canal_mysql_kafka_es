package com.easy.datasource.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easy.datasource.bean.PageDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * </p>
 *
 * @author muchi
 */
public class PageUtil {

    public static <T> Page<T> getPage(IPage<?> pageInterface, List<T> list) {

        Page<T> page = new Page<>();
        BeanUtil.copyProperties(pageInterface, page);
        page.setRecords(list);
        return page;
    }

    public static <T> Page<T> getPage(IPage<?> pageInterface, Class<T> clazz) {

        Page<T> page = new Page<>();
        BeanUtil.copyProperties(pageInterface, page);
        List<T> list = BeanUtil.copyToList(pageInterface.getRecords(), clazz);
        page.setRecords(list);
        return page;
    }

    public static <T, R> Page<R> getPage(IPage<T> pageInterface, Function<? super T, ? extends R> mapper) {

        Page<R> page = new Page<>();
        BeanUtil.copyProperties(pageInterface, page);
        List<R> collect = pageInterface.getRecords().stream().map(mapper).collect(Collectors.toList());
        page.setRecords(collect);
        return page;
    }

    public static <T> Page<T> getPage(PageDTO dto) {

        Page<T> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        if (dto.getOrders() != null && !dto.getOrders().isEmpty()) {
            page.addOrder(dto.getOrders());
        }
        return page;
    }
}