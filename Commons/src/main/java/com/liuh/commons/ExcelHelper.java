/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ExcelHelper
 * Author:   zhangjj
 * Date:     2018/8/23 9:26
 * Description: Excel帮助类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈Excel帮助类〉
 *
 * @author zhangjj
 * @create 2018/8/23
 * @since 1.0.0
 */
public class ExcelHelper {

    private static Logger logger = Logger.getLogger(ExcelHelper.class);

    /**
     * 读取Excel文件的内容
     *
     * @param file Excel文件
     * @param cls  根据Excel数据封装的实体类
     * @return List
     */
    public List readExcel(File file, Class cls, int sheetNum) {
        if (!file.getName().endsWith(".xls")) {
            logger.error(String.format("file format faild: %s", file.getName()));
            return null;
        }
        List dataList = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            HSSFWorkbook book = new HSSFWorkbook(is);
            HSSFSheet sheet = book.getSheetAt(sheetNum);
            int rowLength = sheet.getLastRowNum();
            dataList = new ArrayList(rowLength);
            Field[] fields = cls.getDeclaredFields();
            for (int i = 1; i < rowLength + 1; i++) {
                HSSFRow row = sheet.getRow(i);
                Object o = cls.newInstance();
                for (int j = 0; j < fields.length; j++) {
                    Object data = getCellObject(row.getCell(j), fields[j].getType());
                    fields[j].setAccessible(true);
                    fields[j].set(o, data);
                }
                dataList.add(o);
            }
        } catch (IOException io) {
            logger.error(String.format("open file fail: %s", io.getMessage()));
            io.printStackTrace();
        } catch (IllegalAccessException ill) {
            logger.error(String.format("cls.newInstance() fail: %s", ill.getMessage()));
            ill.printStackTrace();
        } catch (InstantiationException instant) {
            logger.error(String.format("cls.newInstance() fail: %s", instant.getMessage()));
            instant.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * 读取Excel内容默认sheet为0
     *
     * @param file Excel文件
     * @param cls  根据Excel数据封装的实体类
     * @return List
     */
    public List readExcel(File file, Class cls) {
        return readExcel(file, cls, 0);
    }

    /**
     * 数据导出到Excel
     *
     * @param target 导出到的目标文件
     * @param list   数据
     * @return boolean
     */
    public boolean createExcel(File target, List list, String[] titles) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(target);
            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet("sheet1");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = null;
            if (titles != null) {
                for (int i = 0; i < titles.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellType(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue(titles[i]);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                Object obj = list.get(i);
                Class cls = obj.getClass();
                Field[] fields = cls.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    cell = row.createCell(j);
                    cell.setCellType(HSSFCell.ENCODING_UTF_16);
                    fields[j].setAccessible(true);
                    cell.setCellValue(fields[j].get(obj).toString());
                }
            }
            book.write(os);
            os.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 数据导出到Excel
     *
     * @param target 导出到的目标文件
     * @param list   数据
     * @return boolean
     */
    public boolean createExcel(File target, List list) {
        return createExcel(target, list, null);
    }

    /**
     * 根据cell类型获取值
     *
     * @param cell 单元格
     * @return object
     */
    private Object getCellObject(HSSFCell cell, Class type) {
        Object obj = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    obj = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    obj = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    obj = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    return null;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    obj = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    return null;
                default:
                    return null;
            }
            obj = castType(obj, type);
        }
        return obj;
    }

    /**
     * 转换从Execl获取值的类型
     *
     * @param castTo 被转换的值
     * @param type   封装实体类型判断
     */
    private Object castType(Object castTo, Class type) {
        if (type.equals(String.class)) {
            castTo = String.valueOf(castTo);
        } else if (type.equals(Boolean.class)) {
            castTo = Boolean.valueOf(castTo.toString());
        } else if (type.getSuperclass().equals(Number.class)) {
            BigDecimal bigDecimal = new BigDecimal(castTo.toString());
            if (type.equals(Integer.class)) {
                castTo = bigDecimal.intValue();
            } else if (type.equals(Float.class)) {
                castTo = bigDecimal.floatValue();
            } else if (type.equals(Double.class)) {
                castTo = bigDecimal.doubleValue();
            } else if (type.equals(Long.class)) {
                castTo = bigDecimal.longValue();
            }
        }
        return castTo;
    }

}