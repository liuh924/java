/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: User
 * Author:   zhangjj
 * Date:     2018/8/23 10:43
 * Description: 用户
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liuh.test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户〉
 *
 * @author zhangjj
 * @create 2018/8/23
 * @since 1.0.0
 */
public class User {

    private String name;
    private Integer age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}