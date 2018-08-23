/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: StringHelper
 * Author:   zhangjj
 * Date:     2018/8/22 14:44
 * Description: 字符串帮助类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

/**
 * 〈一句话功能简述〉<br> 
 * 〈字符串帮助类〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class StringHelper {


    /**
     *  判断trim过后是否为空字符串和Null值
     * @param s  被判断的字符串
     * @return boolean
     */
    public static boolean trimIsEmpty(String s){
        return isNull(s) || "".equals(s.trim());
    }

    /**
     * 判断是否为字符串和Null值
     * @param s 被判断的字符串
     * @return boolean
     */
    public static boolean isEmpty(String s){
        return isNull(s) || "".equals(s);
    }

    /**
     *  判断是否为Null值
     * @param s 被判断的字符串
     * @return boolean
     */
    public static boolean isNull(String s){
        return null == s;
    }

}