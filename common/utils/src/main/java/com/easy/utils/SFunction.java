package com.easy.utils;

import java.io.Serializable;

@FunctionalInterface
public interface SFunction<T, R> extends Serializable {
    R get(T source);
}