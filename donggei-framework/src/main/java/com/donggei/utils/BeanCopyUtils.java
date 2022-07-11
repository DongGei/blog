package com.donggei.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: BeanCopyUtils
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/10
 **/
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        V re = null;
        try {
            //创建目标对象   newInstance的调的空参构造
            re = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, re);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果

        return re;
    }
    public static<V> List<V> copyBeanList(List<?> sourceList,Class<V> clazz){
        return sourceList.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
