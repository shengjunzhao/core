package com.haole.core.reflect.annotation.lx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: SQLInteger
 * Description: 注解Integer类型的字段
 * Author: shengjunzhao
 * Date: 2018/11/16 17:15
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    //该字段对应数据库表列名
    String name() default "";

    //嵌套注解
    Constraints constraint() default @Constraints;
}
