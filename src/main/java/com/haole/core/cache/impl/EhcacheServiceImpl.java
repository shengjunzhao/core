package com.haole.core.cache.impl;

import com.haole.core.cache.EhcacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * ClassName: EhcacheServiceImpl
 * Description:
 * Author: shengjunzhao
 * Date: 2018/10/15 20:51
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class EhcacheServiceImpl implements EhcacheService {

    @Autowired
    private CacheManager ehcache;

    @Override
    public <K extends Serializable, V extends Serializable> V get(String cacheName, K key) {
        Cache cache = ehcache.getCache(cacheName);
        if (null == cache)
            return null;
        Element element = cache.get(key);
        if (null == element)
            return null;
        return (V) element.getObjectValue();
    }

    @Override
    public <K extends Serializable, V extends Serializable> void put(String cacheName, K key, V value) {
        Cache cache = ehcache.getCache(cacheName);
        if (null == cache)
            return;
        Element element = new Element(key, value);
        cache.put(element);
    }

    @Override
    public <K extends Serializable> boolean put(String cacheName, K key) {
        Cache cache = ehcache.getCache(cacheName);
        if (null == cache)
            return false;
        return cache.remove(key);
    }
}
