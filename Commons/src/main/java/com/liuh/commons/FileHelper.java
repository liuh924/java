/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FileHelper
 * Author:   zhangjj
 * Date:     2018/8/22 11:22
 * Description: 文件帮助类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

import com.liuh.commons.function.ObjectHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈文件帮助类〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class FileHelper {

    private static Logger logger = Logger.getLogger(FileHelper.class);

    /**
     * 读取文件内容
     * @param file 被读取的文件
     * @param handler 回调
     */
    public static void readFile(File file, ObjectHandler handler){
        InputStream is = null;
        try{
           is = new FileInputStream(file);
           BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String temp;
           while((temp = br.readLine()) != null){
               handler.handler(temp);
           }
        }catch(IOException e){
            logger.error(String.format("read file faild: %s,error: %s",file.toString(),e.getMessage()));
            e.printStackTrace();
        }finally {
            if(is != null){
                try{
                    is.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制文件
     * @param source 源文件
     * @param target 目标文件
     */
    public static void copyFile(File source, File target){
        OutputStream os = null;
        FileChannel channel = null;
        try{
            channel = new FileInputStream(source).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(new BigDecimal(source.length()).intValue());
            int size = channel.read(buffer);
            os = new FileOutputStream(target);
            os.write(buffer.array());
            logger.info(String.format("Copy file success is size: %d",size));
        }catch(IOException e){
            logger.error(String.format("Copy file error: %s",e.getMessage()));
            e.printStackTrace();
        }finally{
            try{
                if(os != null){
                    os.close();
                }
                if(channel != null){
                    channel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}