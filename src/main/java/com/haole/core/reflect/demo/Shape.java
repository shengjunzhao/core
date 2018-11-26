package com.haole.core.reflect.demo;

/**
 * ClassName: Shape
 * Description:
 * Author: shengjunzhao
 * Date: 2018/11/26 16:10
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public enum Shape {

    Triangular(3, "三角形"), Rectangle(4, "矩形");

    private int code;
    private String name;

    Shape(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
