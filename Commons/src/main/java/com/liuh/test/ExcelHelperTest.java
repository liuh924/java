/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ExcelHelperTest
 * Author:   zhangjj
 * Date:     2018/8/23 9:37
 * Description: Excel帮助类测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.test;

import com.liuh.commons.ExcelHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Excel帮助类测试〉
 *
 * @author zhangjj
 * @create 2018/8/23
 * @since 1.0.0
 */
public class ExcelHelperTest {

    public static void main(String[] args) {
        testReadExcel();
//        testCreateExcel();
    }


    public static void testReadExcel(){
        try{
            ExcelHelper excelHelper = new ExcelHelper();
            List<User> users = excelHelper.readExcel(new File("D://test.xls"),User.class);
            for(User user: users){
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testCreateExcel(){
        ExcelHelper excelHelper = new ExcelHelper();
        List list = new ArrayList();
        User user = new User();
        user.setName("admin");
        user.setAge(20);
        user.setSex("男");
        list.add(user);
        boolean flag = excelHelper.createExcel(new File("D://test.xls"),list,new String[]{"姓名","年龄","性别"});
        System.out.println(flag);
    }
}