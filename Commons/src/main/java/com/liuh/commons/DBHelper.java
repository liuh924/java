/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DBHelper
 * Author:   zhangjj
 * Date:     2018/9/11 15:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库帮助类〉
 *
 * @author zhangjj
 * @create 2018/9/11
 * @since 1.0.0
 */
public class DBHelper {

    private static Logger logger = Logger.getLogger(DBHelper.class);
    /**
     * 连接信息
     */
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("database");
            driver = bundle.getString("driver");
            url = bundle.getString("url");
            username = bundle.getString("username");
            password = bundle.getString("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            logger.error(String.format("loading driver fail:  %s", e.getMessage()));
            e.printStackTrace();
        } catch (SQLException e2) {
            logger.error(String.format("get connection fail:  %s", e2.getMessage()));
            e2.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     *
     * @param conn 连接
     * @param ps   执行器
     * @param rs   结果集
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("close ResultSet fail: %s", e.getMessage()));
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("close PreparedStatement fail: %s", e.getMessage()));
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("close Connection fail: %s", e.getMessage()));
            e.printStackTrace();
        }
    }


}