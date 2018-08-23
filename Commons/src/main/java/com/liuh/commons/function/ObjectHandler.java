/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ObjectHandler
 * Author:   zhangjj
 * Date:     2018/8/22 11:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.commons.function;

/**
 * 优雅处理数据
 * @author zhangjj
 * @create 2018/8/22
 * @since 1.0.0
 */
public interface ObjectHandler {

    public <T> void handler(T t);

}