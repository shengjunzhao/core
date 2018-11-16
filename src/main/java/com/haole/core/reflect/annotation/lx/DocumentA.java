package com.haole.core.reflect.annotation.lx;

import java.lang.annotation.*;

/**
 * ClassName: DocumentA
 * Description:
 * Author: shengjunzhao
 * Date: 2018/11/16 16:59
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentA {
}
