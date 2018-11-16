package com.haole.core.reflect.annotation.lx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: Constraints
 * Description: 约束注解
 * Author: shengjunzhao
 * Date: 2018/11/16 17:12
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Target(ElementType.FIELD)//只能应用在字段上
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    //判断是否作为主键约束
    boolean primaryKey() default false;

    //判断是否允许为null
    boolean allowNull() default false;

    //判断是否唯一
    boolean unique() default false;
}
