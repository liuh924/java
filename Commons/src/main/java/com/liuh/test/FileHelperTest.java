/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FileHelperTeset
 * Author:   zhangjj
 * Date:     2018/8/22 14:13
 * Description: 文件帮助类测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.test;

import com.liuh.commons.FileHelper;
import com.liuh.commons.function.ObjectHandler;

import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈文件帮助类测试〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class FileHelperTest {

    public static void main(String[] args) {
        testReadFile(new File("E://command.txt"), new ObjectHandler() {
            public <T> void handler(T t) {
                System.out.println(t.toString());
            }
        });

        testCopyFile(new File("E://command.txt"),new File("E://copyCommand.txt"));

    }

    /**
     * 测试逐行读取文件方法
     * @param file
     * @oaram handler
     */
        public static void testReadFile(File file, ObjectHandler handler){
        FileHelper.readFile(file,handler);
    }

    /**
     * 测试复制文件方法
     * @param source 源文件
     * @param target 目标文件
     */
    public static void testCopyFile(File source, File target){
        FileHelper.copyFile(source,target);
    }

}