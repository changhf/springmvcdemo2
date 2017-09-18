package com.changhf.utils;

import java.util.*;

/**
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/09/15
 */
public class CollectionUtil {

    /**
     * 列表是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 列表是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    /**
     * 过滤空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> filterNull(Collection<T> list) {
        if (list == null) {
            return null;
        }

        List<T> newList = new ArrayList<T>();
        for (T t : list) {
            if (t != null) {
                newList.add(t);
            }
        }
        return newList;
    }

    /**
     * 过滤空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> filterEmpty(Collection<T> list) {
        if (list == null) {
            return null;
        }

        List<T> newList = new ArrayList<T>();
        for (T t : list) {
            if (t != null && t.toString().length() > 0) {
                newList.add(t);
            }
        }
        return newList;
    }

    /**
     * 过滤重复项
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> Collection<T> unique(Collection<T> c) {
        if (c != null && c.size() > 0) {
            Map<T, Integer> map = new LinkedHashMap<T, Integer>();
            for (T o : c) {
                map.put(o, 0);
            }
            if (c instanceof Set) {
                return map.keySet();
            } else {
                return new ArrayList<T>(map.keySet());
            }
        }
        return c;
    }


    public static <T> String join(Collection<T> list, String separator, String prefix, String suffix) {
        return prefix + join(list, separator, false) + suffix;
    }

    /**
     * 将list对象用separator连接起来
     *
     * @param list
     * @param separator
     * @param <T>
     * @return
     */
    public static <T> String join(Collection<T> list, String separator) {
        return join(list, separator, false);
    }

    public static <T> String join(Collection<T> list, String separator, boolean filterEmpty) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            int i = 0;
            List<T> newList = filterEmpty ? filterEmpty(list) : new ArrayList<T>(list);
            for (T t : newList) {
                if (t != null) {
                    if (i > 0) {
                        sb.append(separator);
                    }
                    sb.append(t.toString());
                    i++;
                }
            }
        }
        return sb.toString();
    }

    public static <T> String join(T[] arr, String separator, boolean filterEmpty) {
        return join(asList(arr), separator, filterEmpty);
    }

    public static <T> String join(T[] arr, String separator) {
        return join(arr, separator, false);
    }

    public static <T> String join(T[] arr, String separator, int start, int end) {
        List<T> newList = new ArrayList<T>();
        for (int i = 0; i < arr.length; i++) {
            if (i >= start && i < end) {
                newList.add(arr[i]);
            }
        }
        return join(newList, separator);
    }

    /**
     * 字符串分割
     *
     * @param s
     * @param delimiter
     * @return
     */
    public static List<String> split(String s, String delimiter) {
        if (s == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();

        if (s != null && s.length() != 0) {
            String[] arr = s.split(delimiter);
            for (int i = 0; i < arr.length; i++) {
                if (StringUtils.isNotEmpty(arr[i])) {
                    list.add(arr[i].trim());
                }
            }
        }

        return list;
    }

    /**
     * 数组转List对象
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> asList(T... array) {
        if (array == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();
        for (T t : array) {
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 数组转List对象
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> Set<T> asSet(T... array) {
        if (array == null) {
            return null;
        }

        Set<T> set = new HashSet<T>();
        for (T t : array) {
            if (t != null) {
                set.add(t);
            }
        }
        return set;
    }

    /**
     * list对象转为Array对象
     *
     * @param list
     * @return
     */
    public static String[] toArray(Collection<String> list) {
        if (list == null) {
            return null;
        }

        String[] ts = new String[list.size()];
        list.toArray(ts);
        return ts;
    }

    /**
     * 交集
     *
     * @param l1
     * @param l2
     * @param <T>
     * @return
     */
    public static <T> List<T> intersect(List<T> l1, List<T> l2) {
        List<T> list = new ArrayList<T>();
        list.addAll(l1);
        list.retainAll(l2);
        return list;
    }

    /**
     * 并集
     *
     * @param l1
     * @param l2
     * @param <T>
     * @return
     */
    public static <T> List<T> union(List<T> l1, List<T> l2) {
        Map<T, T> map = new LinkedHashMap<T, T>();
        for (T t : l1) {
            map.put(t, t);
        }
        for (T t : l2) {
            map.put(t, t);
        }
        return new ArrayList<T>(map.keySet());
    }

    /**
     * 差集
     *
     * @param l1
     * @param l2
     * @param <T>
     * @return
     */
    public static <T> List<T> diff(List<T> l1, List<T> l2) {
        List<T> list = new ArrayList<T>();
        list.addAll(l1);
        list.removeAll(l2);
        return list;
    }
}
