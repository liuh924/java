/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HtmlHelper
 * Author:   zhangjj
 * Date:     2018/9/4 14:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons;

/**
 * 〈一句话功能简述〉<br>
 * Html帮助类
 *
 * @author zhangjj
 * @create 2018/9/4
 * @since 1.0.0
 */
public class HtmlHelper {


    /**
     * HTML字符转义
     *
     * @param input 需要被转换的html
     * @return 转换后的字符串
     */
    public static String htmlEscape(String input) {
        if (StringHelper.trimIsEmpty(input)) {
            return input;
        }
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("'", "&#39;");   //IE暂不支持单引号的实体名称,而支持单引号的实体编号,故单引号转义成实体编号,其它字符转义成实体名称
        input = input.replaceAll("\"", "&quot;"); //双引号也需要转义，所以加一个斜线对其进行转义
        input = input.replaceAll("\n", "<br/>");  //不能把\n的过滤放在前面，因为还要对<和>过滤，这样就会导致<br/>失效了
        return input;
    }

}