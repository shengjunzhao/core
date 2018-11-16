package com.haole.core.reflect.type.lx;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: ParameterizedTypeBean
 * Description:
 * Author: shengjunzhao
 * Date: 2018/11/15 14:39
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class ParameterizedTypeBean {

    // 下面的 field 的 Type 属于 ParameterizedType
    Map<String, Person> map;
    Set<String> set1;
    Class<?> clz;
    Holder<String> holder;
    List<String> list;
    List<? extends Number> list2;
    // Map<String,Person> map 这个 ParameterizedType 的 getOwnerType() 为 null，
    // 而 Map.Entry<String, String> entry 的 getOwnerType() 为 Map 所属于的 Type。
    Map.Entry<String, String> entry;
    // 下面的 field 的 Type 不属于ParameterizedType
    String str;
    Integer i;
    Set set;
    List aList;

    static class Holder<V> {

    }

}
