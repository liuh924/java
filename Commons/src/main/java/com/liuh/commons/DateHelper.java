/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DateHelper
 * Author:   zhangjj
 * Date:     2018/8/22 14:09
 * Description: 日期帮助类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈日期帮助类〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class DateHelper {

    private static Logger logger = Logger.getLogger(DateHelper.class);

    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static final String DEFAULT_DATE_FORMART  = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String yyyyMMdd = "yyyyMMdd";


    /**
     * 时间格式转换为字符串日期格式
     * @param date 日期
     * @param format 日期格式不填默认为"yyyy-MM-dd hh:mm:ss"
     */
    public static String dateToString(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMART);
        if(!StringHelper.trimIsEmpty(format)){
            simpleDateFormat.applyPattern(format);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串日期格式转换为时间
     * @param dateString 时间字符串
     * @param format 字符串日期格式
     * @return Date or Null
     */
    public static Date stringToDate(String dateString,String format){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMART);
            if(!StringHelper.trimIsEmpty(format)){
                simpleDateFormat.applyPattern(format);
            }
            return simpleDateFormat.parse(dateString);
        }catch(ParseException e){
            logger.error(String.format("String format Date error ====> dateString: %s, format: %s",dateString,format));
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取过去多少年份（年龄）
     * @param  oldDate 以前日期
     * @return year(int)
     */
    public static int overYear(Date oldDate){
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(oldDate);
        int oldYear = calendar.get(Calendar.YEAR);
        int oldMonth = calendar.get(Calendar.MONTH);
        int oldDay = calendar.get(Calendar.DAY_OF_MONTH);
        return nowDay-oldDay>=0 && nowMonth - oldMonth>=0? nowYear-oldYear : nowYear-oldYear-1;
    }

    public static int overYear(String oldDate,String format){
        return overYear(stringToDate(oldDate,format));
    }


}