/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FinallyTest
 * Author:   zhangjj
 * Date:     2018/8/22 17:45
 * Description: try catch finally 测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈try catch finally 测试〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class FinallyTest {

    public static void main(String[] args) {
        boolean flag = testFinally();
        System.out.println("result: " + flag);
    }

    public static boolean testFinally(){
        try{
            System.out.println("execute try code block");
//            int i = 1/0;
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("execute finally code block");
        }
        System.out.println("execute end code block");
        return false;
    }

}