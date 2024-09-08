package com.easy.utils.lang;


import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * List工具类
 * </p>
 *
 * @author muchi
 */
public class ListUtil {

    /**
     * List二维数组
     *
     * @param list 集合
     * @return int[][]
     */
    public static int[][] toTwoDimensionalArray(List<Integer> list) {

        int[][] twoDimensionalArray = new int[1][];
        if (CollectionUtil.isEmpty(list)) {
            return twoDimensionalArray;
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        twoDimensionalArray[0] = array;
        return twoDimensionalArray;
    }

    /**
     * 将传入的 List 按照给定的 index 拆分成 2 个子 List
     * 例如 list = [1, 2, 3, 4, 5], index = 3
     * 则会得到: [[1, 2, 3],[4, 5]]
     * list = [1, 2, 3, 4, 5]  , index = 2
     * 则会得到: [[1, 2], [3, 4, 5]]
     * list = [1, 2, 3, 4, 5]  , index <= 0
     * 则会得到: [[1, 2, 3, 4, 5]]
     */
    public static <T> List<List<T>> subList(List<T> list, Integer index) {

        List<List<T>> lists = new ArrayList<>();
        if (0 == index) {
            return new ArrayList<>();
        }
        if (list.size() <= index || index < 0) {
            lists.add(list);
            return lists;
        }
        List<T> listLeft = new ArrayList<>();
        List<T> listRight = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i < index) {
                listLeft.add(list.get(i));
            } else {
                listRight.add(list.get(i));
            }
        }
        lists.add(listLeft);
        lists.add(listRight);
        return lists;
    }

    public static <T> List<List<T>> transpose(List<List<T>> listList) {

        List<List<T>> newListList = new ArrayList<>();
        for (List<T> list : listList) {
            for (int j = 0; j < list.size(); j++) {
                T t = list.get(j);
                if (newListList.size() <= j) {
                    List<T> newList = new ArrayList<>();
                    newList.add(t);
                    newListList.add(newList);
                } else {
                    List<T> newList = newListList.get(j);
                    newList.add(t);
                    newListList.set(j, newList);
                }
            }
        }
        return newListList;
    }

    public static <T> List<T> addMultiple(T t, int num) {

        List<T> list = new ArrayList<>();
        if (num < 0) {
            return list;
        }
        for (int i = 0; i < num; i++) {
            list.add(t);
        }
        return list;
    }

    /**
     * 将传入的 List 按照给定的 size 拆分成多个子 List
     * 例如 list = [1, 2, 3, 4, 5], per=3
     * 则会得到: [[1, 2, 3],[4, 5]]
     * list = [1, 2, 3, 4, 5]  , per = 2
     * 则会得到: [[1, 2], [3, 4], [5]]
     * list = [1, 2, 3, 4, 5]  , per <= 0
     * 则会得到: [[1, 2, 3, 4, 5]]
     */
    public static <T> List<List<T>> splitList(List<T> list, int per) {

        if (per <= 0) {
            per = list.size() + 1;
        }
        List<List<T>> returnList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return returnList;
        }
        int count = list.size() / per;
        int yu = list.size() % per;
        for (int i = 0; i <= count; i++) {
            List<T> subList;
            if (i == count) {
                subList = list.subList(i * per, i * per + yu);
            } else {
                subList = list.subList(i * per, per * (i + 1));
            }
            returnList.add(subList);
        }
        return returnList;
    }

    public static <T> List<T> page(List<T> list, long current, long size) {

        return page(list, (int) current, (int) size);
    }

    public static <T> List<T> page(List<T> list, int current, int size) {

        if (current < 0 || size < 0) {
            return new ArrayList<>();
        }
        List<List<T>> lists = splitList(list, size);
        if (lists.size() < current) {
            return new ArrayList<>();
        }
        return lists.get(current - 1);
    }

    public static <T> List<T> intersection(List<T> left, List<T> right) {

        List<T> list = new ArrayList<>();
        if (left == null || right == null || left.isEmpty() || right.isEmpty()) {
            return list;
        }
        HashSet<T> set = new HashSet<>(left);
        right.forEach(k -> {
            if (set.contains(k)) {
                list.add(k);
            }
        });
        return list;
    }

    /**
     * 将 List 转换为 String[]
     *
     * @param resources List
     * @return String[]
     */
    public static String[] convertToArray(List<String> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            String[] result = new String[resources.size()];
            return resources.toArray(result);
        } else {
            return new String[]{};
        }
    }

    public static <T> List<T> subList(List<T> list, int index, int length) {

        List<T> returnList = new ArrayList<>();
        if (list.isEmpty() || index < 0 || length < 0) {
            return returnList;
        }
        if (list.size() < index || list.size() < length) {
            return returnList;
        }

        for (int i = 0; i < length; i++) {
            returnList.add(list.get(index + i));
        }
        return returnList;
    }
}