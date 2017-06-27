package pro.zkey.core.cache;

import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 获取高性能简单Map结构数据
 * 2017-06-22 11:58:42
 * @author PS
 */
public interface CacheMapData {

    /**
     * 在Web缓存中创建实例
     * @param map 初始化数据
     */
    void createInWebCache(Map<String, String> map);
    /**
     * 设置数据
     * @param field
     * @param value
     */
    void set(String field,String value);

    /**
     * 批量设置数据,若存在field相同则直接进行覆盖
     * @param map
     */
    void set(Map<String, String> map);
    /**
     * 删除数据
     * @param fields
     */
    void del(String... fields);

    /**
     * 获取数据
     * @param field
     * @return
     */
    String get(String field);

    /**
     * 转化为Map对象
     * @return
     */
    Map<String,String> toMap();

    /**
     * 返回元素个数
     * @return 元素个数
     */
    long size();
}
