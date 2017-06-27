package pro.zkey.core.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.zkey.core.cache.CacheMapData;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 获取高性能简单Map结构数据
 * 2017-06-22 11:58:42
 * @author PS
 */
public class CacheMapDataImpl implements CacheMapData {
    // 日志
    private Logger logger = LoggerFactory.getLogger(CacheMapDataImpl.class);
    // 设置该对象对应的Web缓存key
    // 所有操作均基于该key进行
    private String key = "";
    // jedis链接池
    private JedisPool pool = null;
    public CacheMapDataImpl(String realKey,JedisPool pool){
        this.key = realKey;
        this.pool = pool;
    }

    /**
     * 在Web缓存中创建实例
     * @param map 初始化数据
     */
    public void createInWebCache(Map<String, String> map) {
        Jedis jedis = null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.del(key);
            // 无值则临时赋默认值确保缓存被创建
            if (map.isEmpty()){
                map.put("__default","__default");
                jedis.hmset(key,map);
                del("__default");
            }else{
                jedis.hmset(key,map);
            }
        } catch (Exception ex) {
            logger.error("在Web缓存中创建实例:{}\n",map, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 设置数据
     *
     * @param field
     * @param value
     */
    public void set(String field, String value) {
        Jedis jedis = null;
        try {
            if (value==null)throw new Exception("value不能为null");
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.hset(key,field,value);
        } catch (Exception ex) {
            logger.error("设置数据field:{}\nvalue:{}\n",field,value, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 批量设置数据,若存在field相同则直接进行覆盖
     * @param map
     */
    public void set(Map<String, String> map) {
        Jedis jedis = null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.hmset(key,map);
        } catch (Exception ex) {
            logger.error("批量设置数据,若存在field相同则直接进行覆盖:{}\n",map, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 删除数据
     * @param fields
     */
    public void del(String... fields) {
        Jedis jedis = null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.hdel(key,fields);
        } catch (Exception ex) {
            logger.error("删除字段fields:",fields, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 获取数据
     *
     * @param field
     * @return
     */
    public String get(String field) {
        Jedis jedis = null;
        String rs=null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            rs = jedis.hget(key,field);
        } catch (Exception ex) {
            logger.error("获取值fields失败\nfield:{}",field, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 转化为Map对象
     *
     * @return
     */
    public Map<String, String> toMap() {
        Jedis jedis = null;
        Map<String, String> rs=null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            rs = jedis.hgetAll(key);
        } catch (Exception ex) {
            logger.error("获取值fields失败\n", ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 返回元素个数
     * @return 元素个数
     */
    public long size(){
        Jedis jedis = null;
        long rs=0;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            rs = jedis.hlen(key);
        } catch (Exception ex) {
            logger.error("获取值元素个数失败\n", ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }
}
