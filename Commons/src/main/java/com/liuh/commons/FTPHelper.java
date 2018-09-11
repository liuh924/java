/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FTPHelper
 * Author:   zhangjj
 * Date:     2018/8/22 16:36
 * Description: FTP帮助类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈FTP帮助类〉
 *
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public class FTPHelper {

    private Logger logger = Logger.getLogger(FTPHelper.class);

    /**
     * @param hostName    FTP服务器hostName
     * @param port        FTP服务器端口
     * @param userName    FTP服务器用户名
     * @param password    FTP服务器密码
     * @param basePath    FTP服务器基础目录
     * @param filePath    FTP服务器文件存放路径，如（/2018/08/22）文件的路径为basePath+filePath
     * @param fileName    上传到FTP服务器文件名
     * @param inputStream 输入流
     * @return boolean
     */
    public static boolean uploadFile(String hostName, int port, String userName, String password, String basePath,
                                     String filePath, String fileName, InputStream inputStream) {
        FTPClient ftp = new FTPClient();
        try {
            //连接
            ftp.connect(hostName, port);
            //登录
            ftp.login(userName, password);
            //获取状态码
            int replyCode = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftp.disconnect();
                return false;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (StringHelper.trimIsEmpty(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (ftp.makeDirectory(tempPath)) {
                            return false;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件类型为二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(fileName, inputStream)) {
                return false;
            }
            ftp.logout();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param hostName   FTP服务器hostName
     * @param port       FTP服务器端口
     * @param userName   FTP服务器用户名
     * @param password   FTP服务器密码
     * @param remotePath FTP服务器文件路径
     * @param fileName   FTP服务器文件名
     * @param localPath  本机保存路径
     * @return boolean
     */
    public static boolean downloadFile(String hostName, int port, String userName, String password,
                                       String remotePath, String fileName, String localPath) {
        FTPClient ftp = new FTPClient();
        OutputStream os = null;
        try {
            ftp.connect(hostName, port);
            ftp.login(userName, password);
            int status = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(status)) {
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals("fileName")) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    os = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), os);
                }
            }
            ftp.logout();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}