package com.easy.datasource.dao;


import com.easy.framework.bean.base.BaseEntity;

/**
 * </p>
 *
 * @author muchi
 */
public class BaseEntityProvider extends BaseProvider<BaseEntity> {

    private static final BaseEntityProvider provider = new BaseEntityProvider();

    public static BaseEntityProvider get() {
        return provider;
    }
}