package com.haole.core.treenode;

/**
 * ClassName: Entry
 * Description:
 * Author: shengjunzhao
 * Date: 2018/9/12 14:17
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class Entry<K, V> extends Node<K, V> {
    Entry<K, V> before, after;

    Entry(int hash, K key, V value, Node<K, V> next) {
        super(hash, key, value, next);
    }
}