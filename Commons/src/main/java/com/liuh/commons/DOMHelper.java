/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DOMUtil
 * Author:   zhangjj
 * Date:     2018/9/10 11:40
 * Description: dom工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈dom工具类〉
 *
 * @author zhangjj
 * @create 2018/9/10
 * @since 1.0.0
 */
public class DOMHelper {

    private static Logger logger = Logger.getLogger(DOMHelper.class);

    /**
     * 创建一个document对象
     *
     * @param annotation 注释
     * @param rootNode   根节点
     * @return document
     */
    public static Document createDocument(String annotation, String rootNode) {
        Document document = DocumentHelper.createDocument();
        document.addComment(annotation);
        document.addElement(rootNode);
        return document;
    }

    /**
     * 通过文件获取document对象
     *
     * @param path 文件的位置
     * @return document
     */
    public static Document getXmlByFilePath(String path) {
        if (StringHelper.trimIsEmpty(path)) {
            return null;
        }
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 获取指定节点的所有子节点
     *
     * @param node 指定节点
     * @return list(所有子节点)
     */
    public static List<Element> getChildElements(Element node) {
        if (null == node) {
            return null;
        }
        return node.elements();
    }

    /**
     * 获取指定节点的子节点
     *
     * @param node      指定节点
     * @param childNode 子节点名称
     * @return Element(子节点)
     */
    public static Element getChildElement(Element node, String childNode) {
        if (null == node || StringHelper.trimIsEmpty(childNode)) {
            return null;
        }
        return node.element(childNode);
    }

    /**
     * 获取指定节点的所有属性值
     *
     * @param node   指定节点
     * @param params 所有属性值名称
     * @return Map(所有属性值)
     */
    public static Map<String, String> getAttributes(Element node, String... params) {
        if (node == null || 0 == params.length) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            map.put(param, node.attributeValue(param));
        }
        return map;
    }


    /**
     * 获取指定节点的单个属性值
     *
     * @param node  指定节点
     * @param param 属性值名称
     * @return 属性值
     */
    public static String getAttributte(Element node, String param) {
        if (node == null || StringHelper.trimIsEmpty(param)) {
            return null;
        }
        return node.attributeValue(param);
    }

    /**
     * 为指定节点添加子节点
     *
     * @param parent     指定节点
     * @param childName  子节点名称
     * @param childValue 子节点值（可以为null）
     * @return Element(子节点)
     */
    public static Element addChild(Element parent, String childName, String childValue) {
        Element child = parent.addElement(childName);
        child.setText(StringHelper.trimIsEmpty(childValue) ? "" : childValue);
        return child;
    }

    /**
     * Document对象转换为xml报文串
     *
     * @param document document对象
     * @param charset  字符集
     * @return 字符串
     */
    public static String documentToString(Document document, String charset) {
        StringWriter writer = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        try {
            xmlWriter.write(document);
            xmlWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }

    /**
     * Document对象转换为xml报文串去掉<?xml....?/>
     *
     * @param document document对象
     * @param charset  字符集
     * @return 字符串
     */
    public static String documentToStringNoDeclareHeader(Document document, String charset) {
        return documentToString(document, charset).replaceAll("\\s*<[^<>]+>\\s*", "");
    }

    /**
     * 解析XMl字符串为Document对象
     *
     * @param xml xml字符串
     * @return 根节点
     */
    public static Element parseXml(String xml) {
        StringReader reader = new StringReader(xml);
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document == null ? null : document.getRootElement();
    }

    /**
     * 获取Element对象的Text值
     *
     * @param element 节点对象
     * @param tag     标签名
     * @return 值
     */
    public static String getText(Element element, String tag) {
        Element e = element.element(tag);
        return e == null ? null : e.getText();
    }

    /**
     * 获取Element对象trim()过后的Text值
     *
     * @param element 节点对象
     * @param tag     标签名
     * @return 值
     */
    public static String getTextTrim(Element element, String tag) {
        Element e = element.element(tag);
        return e == null ? null : e.getTextTrim();
    }

    /**
     * Document对象写入文件
     *
     * @param document document对象
     * @param path     文件路径
     */
    public static void writeDocumentToFile(Document document, String path) {
        if (document == null || StringHelper.trimIsEmpty(path)) {
            logger.error(String.format("document write file fail： document(%s)   path(%s)", document, path));
            return;
        }
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(path));
            writer.write(document);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}