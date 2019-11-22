package com.kongque.util;

import net.sf.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaxt
 * @date 2018/8/15.
 */
public class CollectionUtil {
    /**
     * 获取a不在b中的元素
     * @param a
     * @param b
     * @return
     */
    public static String[] getANotInB(JSONArray a, JSONArray b){
        if (a==null) return new String[0];
        if (b==null) return (String[])a.toArray(new String[a.size()]);
        List<String> list = new ArrayList<>();
        for (String s : (String[])a.toArray(new String[a.size()])) {
            if (!isAInB(s,(String[])b.toArray(new String[a.size()]))){
                list.add(s);
            }
        }
        return list.toArray(new String[list.size()]);
    }
    /**
     * 获取a不在b中的元素
     * @param a
     * @param b
     * @return
     */
    public static String[] getANotInB(String[] a, String[] b){
        if (a==null) return new String[0];
        if (b==null) return a;
        List<String> list = new ArrayList<>();
        for (String s : a) {
            if (!isAInB(s,b)){
                list.add(s);
            }
        }
        return list.toArray(new String[list.size()]);
    }
    /**
     * 获取a不在b中的元素,返回list
     * @param a
     * @param b
     * @return
     */
    public static <T> List<T> getANotInB(List<T> a, List<T> b){
        if (a==null) return new ArrayList<T>();
        if (b==null) return a;
        List<T> list = new ArrayList<>();
        for (T s : a) {
            if (!isAInB(s,b)){
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 获取a不在b中的元素,返回数组
     * @param a
     * @param b
     * @return
     */
    public static <T> List<T> getANotInB(T[] a, List<T> b){
        if (a==null || b==null) return new ArrayList<T>();
        List<T> list = new ArrayList<>();
        for (T s : a) {
            if (!isAInB(s,b)){
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 获取a不在b中的元素,返回数组
     * @param a
     * @param b
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K,V> List<K> getANotInB(K[] a, Map<K,V> b){
        if (a==null || b==null) return new ArrayList<K>();
        List<K> list = new ArrayList<>();
        for (K s : a) {
            if (b.get(s)==null){
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 获取a不在b中的元素,返回Map
     * @param a
     * @param b
     * @return
     */
    public static <K,V> Map<K,V> getANotInB(Map<K,V> a, K[] b){
        if (a==null || b==null) return a;
        Map<K,V> map = new HashMap<K, V>();
        for (K s : a.keySet()) {
            if (!isAInB(s,b)){
                map.put(s,a.get(s));
            }
        }
        return map;
    }

    /**
     * 获取a不在b中的元素,返回list
     * @param a
     * @param b
     * @return
     */
    public static <T> List<T> getANotInB(List<T> a, T[] b){
        if (a==null || b==null) return a;
        List<T> list = new ArrayList<>();
        for (T s : a) {
            if (!isAInB(s,b)){
                list.add(s);
            }
        }
        return list;
    }
    /**
     * 获取a与b不同的元素
     * @param a
     * @param b
     * @return
     */
    public static String[] getABDiff(String[] a, String[] b){
        if (a==null) return b;
        if (b==null) return a;
        List<String> list = new ArrayList<>();
        for (String s : a) {
            if (!isAInB(s,b)){
                list.add(s);
            }
        }
        for (String s : b){
            if (!isAInB(s,a)){
                list.add(s);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 判断a是否属于b
     * @param a
     * @param b
     * @return
     */
    public static <T> boolean isAInB(T a, T[] b){
        for (T s : b) {
            if (a.equals(s)){
                return true;
            }
        }
        return false;
    }
    /**
     * 判断a是否属于b
     * @param a
     * @param b
     * @return
     */
    public static <T> boolean isAInB(T a, List<T> b){
        for (T s : b) {
            if (a.equals(s)){
                return true;
            }
        }
        return false;
    }
}
