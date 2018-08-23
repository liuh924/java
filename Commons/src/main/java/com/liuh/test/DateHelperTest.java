/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DateHelperTest
 * Author:   zhangjj
 * Date:     2018/8/22 15:46
 * Description: 日期帮助类测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.test;

import com.liuh.commons.DateHelper;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈日期帮助类测试〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class DateHelperTest {

    public static void main(String[] args) {
        testOverYear();
        testDateToString();
        testOverYear("1995-09-24",DateHelper.yyyy_MM_dd);
    }


    public static void testOverYear(){
        int year = DateHelper.overYear(DateHelper.stringToDate("1995-08-22",DateHelper.yyyy_MM_dd));
        System.out.println("over year: " + year);
    }

    public static void testDateToString(){
        String dateString = DateHelper.dateToString(new Date(),null);
        System.out.println(dateString);
    }

    public static void testOverYear(String dateString,String format){
        int year = DateHelper.overYear(dateString,format);
        System.out.println("over year:" + year);
    }
}