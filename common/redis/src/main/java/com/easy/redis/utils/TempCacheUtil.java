package com.easy.redis.utils;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * </p>
 *
 * @author muchi
 */
public class TempCacheUtil {

    private static final String UNIFIED_CACHE = "UNIFIED_CACHE:";

    @SneakyThrows
    private static <T> T getCache(Callable<T> callable, Duration duration) {

        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String redisKey = UNIFIED_CACHE + stackTraceElement.getClassName() + ":" + stackTraceElement.getMethodName();
        T cacheObject = RedisUtils.getCacheObject(redisKey);
        if (Objects.nonNull(cacheObject)) {
            return cacheObject;
        }

        // 调用方法
        T call = callable.call();

        // 写缓存
        if (Objects.nonNull(call)) {
            RedisUtils.deleteObject(redisKey);
            RedisUtils.setCacheObject(redisKey, call, duration);
        }
        return call;
    }

    public static <T> T getShortTimeCache(Callable<T> callable) {

        return getCache(callable, Duration.ofSeconds(5L));
    }
}