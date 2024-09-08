package com.easy.utils.lang;

/**
 * 雪花算法ID生成
 *
 * @author muchi
 */
public class SnowflakeIdUtil {

    private static final SnowflakeUtil ID_WORKER = new SnowflakeUtil();

    private SnowflakeIdUtil() {
    }

    public static SnowflakeIdUtil getInstance() {
        return InnerClass.INS;
    }

    public String getNextId() {
        return String.valueOf(ID_WORKER.nextId());
    }

    public Long getNextLongId() {
        return ID_WORKER.nextId();
    }

    private static class InnerClass {
        private static final SnowflakeIdUtil INS = new SnowflakeIdUtil();

    }
}