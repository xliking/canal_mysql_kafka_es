package com.easy.utils.math;


import org.apache.commons.math3.exception.ZeroException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 提供加减乘除、平均值、最大值、最小值计算
 * </p>
 *
 * @author muchi
 */
public final class MathUtil {

    /**
     * 默认的除法精确度
     */
    private static final int DEF_DIV_SCALE = 10;

    private MathUtil() {
        throw new IllegalAccessError("MathUtils.class");
    }

    /**
     * 精确加法运算，计算多个数值总和，null 会被视为 0
     *
     * @param valList 被加数集合
     * @return 两个参数的和(String)
     */
    public static BigDecimal sum(Object... valList) {

        BigDecimal b = BigDecimal.ZERO;
        if (null == valList || valList.length == 0) {
            return b;
        }
        List<Object> list = tranObjToList(valList);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        for (Object val : list) {
            b = b.add(tranObjectToBigDecimal(val));
        }
        return b;
    }

    /**
     * 精确减法运算，null 会被视为 0
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差(String)
     */
    public static BigDecimal subtract(Object v1, Object v2) {

        if (null == v2) {
            throw new ZeroException();
        }
        return tranObjectToBigDecimal(v1).subtract(tranObjectToBigDecimal(v2));
    }

    /**
     * 精确乘法运算，计算多个数值的积，null 会被视为 0
     *
     * @param valList 乘数
     * @return 两个参数的积(String)
     */
    public static BigDecimal multiply(Object... valList) {

        BigDecimal b1 = BigDecimal.ZERO;
        if (null == valList || valList.length == 0) {
            return b1;
        }
        b1 = BigDecimal.ONE;
        List<Object> list = tranObjToList(valList);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        for (Object val : list) {
            b1 = b1.multiply(tranObjectToBigDecimal(val));
        }
        return b1;
    }

    /**
     * （相对）精确除法运算。当发生除不尽情况时，由 scale 参数指 定精度，以后数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    private static BigDecimal divide(BigDecimal v1, BigDecimal v2, Integer scale) {

        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为 0");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于 0");
        }
        return v1.divide(v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * （相对）精确除法运算。当发生除不尽情况时，精确到小数点以后 2 位，以后数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(String)
     */
    public static BigDecimal divide(Object v1, Object v2) {

        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * （相对）精确除法运算。当发生除不尽情况时，由 scale 参数指 定精度，以后数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(String)
     */
    public static BigDecimal divide(Object v1, Object v2, Integer scale) {

        return divide(tranObjectToBigDecimal(v1), tranObjectToBigDecimal(v2), scale);
    }

    /**
     * 平均数
     */
    public static BigDecimal avg(Object... valList) {

        if (null == valList || valList.length == 0) {
            return BigDecimal.ZERO;
        }
        List<Object> list = tranObjToList(valList);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return divide(sum(valList), list.size());
    }

    /**
     * 最大值
     */
    public static BigDecimal max(Object... valList) {

        BigDecimal max = BigDecimal.ZERO;
        if (null == valList || valList.length == 0) {
            return max;
        }
        List<Object> list = tranObjToList(valList);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        max = tranObjectToBigDecimal(list.get(0));
        for (Object val : list) {
            if (null == val) {
                continue;
            }
            BigDecimal temp = tranObjectToBigDecimal(val);
            if (temp.compareTo(max) > 0) {
                max = temp;
            }
        }
        return max;
    }

    /**
     * 最小值
     */
    public static BigDecimal min(Object... valList) {

        BigDecimal min = BigDecimal.ZERO;
        if (null == valList || valList.length == 0) {
            return min;
        }
        List<Object> list = tranObjToList(valList);
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        min = tranObjectToBigDecimal(list.get(0));
        for (Object val : list) {
            if (null == val) {
                continue;
            }
            BigDecimal temp = tranObjectToBigDecimal(val);
            if (temp.compareTo(min) < 0) {
                min = temp;
            }
        }
        return min;
    }

    /**
     * 判断字符串是否为空(不依赖第三方)
     */
    private static boolean isBlank(String str) {

        return null == str || str.trim().isEmpty();
    }

    /**
     * 比较大小，o1 > o2 true
     */
    public static boolean moreThan(Object o1, Object o2) {

        return tranObjectToBigDecimal(o1).compareTo(tranObjectToBigDecimal(o2)) > 0;
    }

    /**
     * 比较大小，o1 < o2 true
     */
    public static boolean lessThan(Object o1, Object o2) {

        return tranObjectToBigDecimal(o1).compareTo(tranObjectToBigDecimal(o2)) < 0;
    }

    /**
     * 比较大小，o1 = o2 true
     */
    public static boolean equal(Object o1, Object o2) {

        return tranObjectToBigDecimal(o1).compareTo(tranObjectToBigDecimal(o2)) == 0;
    }

    /**
     * 将不同类型数字转为字符串
     *
     * @param o        数字
     * @param newScale 保留小数位数
     */
    public static String format(Object o, int newScale, RoundingMode roundingMode) {

        if (null == o) {
            return null;
        }
        BigDecimal bigDecimal = tranObjectToBigDecimal(o);
        return bigDecimal.setScale(newScale, roundingMode).stripTrailingZeros().toPlainString();
    }

    /**
     * 将不同类型数字转为字符串，默认保留 2 位小数
     *
     * @param o        数字
     * @param newScale 是否取整，true 小数舍去，false 小数
     */
    public static String format(Object o, int newScale) {

        if (null == o) {
            return null;
        }
        return format(o, newScale, RoundingMode.HALF_UP);
    }

    /**
     * 将不同类型数字转为字符串，默认保留 2 位小数
     *
     * @param o 数字
     */
    public static String format(Object o) {

        if (null == o) {
            return null;
        }
        return format(o, 2);
    }

    /**
     * 将不同类型数字转为字符串，直接输出。null -> 0
     *
     * @param o 数字
     */
    public static String toString(Object o) {

        if (o == null) {
            return "0";
        }
        return tranObjectToBigDecimal(o).toPlainString();
    }

    /**
     * 将 obj 转为 BigDecimal，null 默认返回 null
     */
    public static BigDecimal toBigDecimal(Object o) {

        return tranObjectToBigDecimal(o);
    }

    /**
     * 将 obj 转为 Integer，null 默认返回 null
     */
    public static Integer toInteger(Object o) {

        BigDecimal bigDecimal = tranObjectToBigDecimal(o);
        return bigDecimal.intValue();
    }

    /**
     * 将对象转换为BigDecimal
     *
     * @param o 对象
     * @return BigDecimal
     */
    private static BigDecimal tranObjectToBigDecimal(Object o) {

        BigDecimal b;
        if (null == o) {
            return null;
        }
        if (o instanceof String) {
            b = isBlank((String) o) ? null : new BigDecimal((String) o);
        } else if (o instanceof BigDecimal) {
            b = (BigDecimal) o;
        } else if (o instanceof Byte) {
            b = BigDecimal.valueOf((Byte) o);
        } else if (o instanceof Short) {
            b = BigDecimal.valueOf((Short) o);
        } else if (o instanceof Double) {
            b = BigDecimal.valueOf((Double) o);
        } else if (o instanceof Float) {
            b = BigDecimal.valueOf((Float) o);
        } else if (o instanceof Integer) {
            b = new BigDecimal((Integer) o);
        } else if (o instanceof Long) {
            b = new BigDecimal((Long) o);
        } else if (o instanceof List) {
            throw new RuntimeException("无法将集合转换为 BigDecimal");
        } else {
            throw new RuntimeException("数据转换时遇到暂不支持的数据类型");
        }
        return b;
    }

    private static List<Object> tranObjToList(Object... valList) {

        List<Object> list = new ArrayList<>();
        if (null == valList) {
            return list;
        }
        for (Object val : valList) {
            if (val instanceof List<?> valObjList) {
                list.addAll(valObjList);
            } else {
                list.add(val);
            }
        }
        return list;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static class Formula {

        public static BigDecimal calc(String s) {

            // 中缀 -> 后缀
            List<String> transferResult = transferInfillToSuffix(s);
            return multipleCalculate(transferResult);
        }

        public static BigDecimal calc(Object... s) {

            // 中缀 -> 后缀
            if (0 == s.length) {
                throw new IllegalArgumentException("Expression cannot be empty");
            }
            Iterator<Object> iterator = Arrays.asList(s).iterator();
            StringBuilder sb = new StringBuilder(objToStr(iterator.next()));
            while (iterator.hasNext()) {
                sb.append(" ");
                sb.append(objToStr(iterator.next()));
            }
            return calc(sb.toString());
        }

        private static String objToStr(Object o) {

            if (o instanceof BigDecimal) {
                return MathUtil.toString(o);
            }
            return String.valueOf(o);
        }

        private static List<String> transferInfillToSuffix(String mathStr) {

            List<String> result = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            if (mathStr == null || mathStr.isEmpty()) {
                return null;
            }
            String[] arr = mathStr.split(" ");
            for (String s : arr) {
                if (!isSymbol(s)) {
                    result.add(s);
                } else if ("(".equals(s)) {
                    stack.push(s);
                } else if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
                    if (!stack.isEmpty()) {
                        String stackTop = stack.pop();
                        if (compare(s, stackTop)) {
                            stack.push(stackTop);
                            stack.push(s);
                        } else {
                            result.add(stackTop);
                            stack.push(s);
                        }
                    } else {
                        stack.push(s);
                    }
                } else if (")".equals(s)) {
                    while (!stack.isEmpty()) {
                        String item = stack.pop();
                        if (!"(".equals(item)) {
                            result.add(item);
                        } else {
                            break;
                        }
                    }
                }
            }
            while (!stack.isEmpty()) {
                result.add(stack.pop());
            }
            return result;
        }

        private static boolean isSymbol(String s) {

            return "(".equals(s) || ")".equals(s) || "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s);
        }

        /**
         * 比较优先级
         */
        private static boolean compare(String s, String item) {
            if ("(".equals(item)) {
                return true;
            }
            if ("*".equals(s) || "/".equals(s)) {
                return "+".equals(item) || "-".equals(item);
            }
            return false;
        }

        private static BigDecimal multipleCalculate(List<String> transferToPostfix) {

            Stack<Object> stack = new Stack<>();
            Object a, b;
            for (String value : transferToPostfix) {
                switch (value) {
                    case "+" -> {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(MathUtil.sum(b, a));
                    }
                    case "-" -> {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(MathUtil.subtract(b, a));
                    }
                    case "*" -> {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(MathUtil.multiply(b, a));
                    }
                    case "/" -> {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(MathUtil.divide(b, a));
                    }
                    default -> stack.push(value);
                }
            }
            return (BigDecimal) stack.pop();
        }
    }
}