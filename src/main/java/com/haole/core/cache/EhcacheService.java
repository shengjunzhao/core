package com.haole.core.cache;

import java.io.Serializable;

/**
 * ClassName: EhcacheService
 * Description:
 *
 * 在spring xml 配置文件中配置下面信息
 *  <bean id="ehCacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
 *      <property name="configLocation" value="classpath:ehcache.xml"></property>
 *  </bean>
 *
 * Author: shengjunzhao
 * Date: 2018/10/15 20:17
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface EhcacheService {

    <K extends Serializable, V extends Serializable> V get(String cacheName, K key);

    <K extends Serializable, V extends Serializable> void put(String cacheName, K key, V value);

    <K extends Serializable> boolean put(String cacheName, K key);
}
