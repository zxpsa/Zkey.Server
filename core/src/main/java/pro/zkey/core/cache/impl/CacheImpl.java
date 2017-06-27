package pro.zkey.core.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.zkey.core.cache.Cache;
import pro.zkey.core.cache.CacheListData;
import pro.zkey.core.cache.CacheMapData;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

/**
 * 缓存默认实现
 *
 * @author PS
 * @date 2017-06-20 10:28:53
 */
public class CacheImpl implements Cache {
    // 日志
    private Logger logger = LoggerFactory.getLogger(CacheImpl.class);
    // 链接池
    private static JedisPool pool = null;
    // 模块分组Id 为后期模块化缓存分片预留分割
    private String groupId="def";
    // 模块Id
    private String moduleId="mod";
    // 缓存数据默认保存时间 30天
    private long defaultExpiresTime=2592000;
    /**
     * 构造缓存实例
     * 注: groupId,moduleId 为后期模块化缓存分片预留分割基点,
     * 默认与项目模块名保持一致
     */
    public CacheImpl(){
        init();
    }

    /**
     * 构造缓存实例
     * 注: groupId,moduleId 为后期模块化缓存分片预留分割基点,基本与项目模块名保持一致
     * @param groupId 模块分组Id
     * @param moduleId 模块Id
     */
    public CacheImpl(String groupId,String moduleId) {
        this.groupId=groupId;
        this.moduleId=moduleId;
        init();
    }

    /**
     * 缓存对象初始化
     * 链接池为单例
     */
    private void init(){
        Jedis jedis = null;
        if (pool == null) {
            try {
                logger.info("开始初始化jedis链接池");
                JedisPoolConfig config = new JedisPoolConfig();
                //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
                //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
                config.setMaxTotal(500);
                //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
                config.setMaxIdle(5);
                //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
                config.setMaxWaitMillis(1000 * 100);
                //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
                config.setTestOnBorrow(true);
                pool = new JedisPool(config, "119.23.49.184", 6379, 1000, "55748562");
                jedis = pool.getResource();
                logger.info("jedis：\n最大实例数：{}\n最大空闲实例数：{}\n最大等待时间：{}\n服务器地址：{}", 500, 5, 1000 * 100, "123123");
            } catch (Exception e) {
                logger.info("初始化jedis链接池失败:\n", e);
            } finally {
                jedis.close();
            }
            logger.info("初始化jedis链接池完成");
        }
    }

    /**
     * 获取缓存服务器中真实Key
     * @param key 业务key
     * @return 真实Key
     */
    public String getRealKey(String key){
        return this.groupId +"_"+ this.moduleId+"_"+ key;
    }

    /**
     * 设置缓存超时时间
     * @param key
     * @param expiresTime
     */
    public void setExpire(String key,long expiresTime){
        Jedis jedis = null;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            jedis.expire(getRealKey(key),(int)expiresTime);
        } catch (Exception ex) {
            logger.error("设置缓存超时时间失败",ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value
     */
    public void set(String key, String value) {
        set(key,value,defaultExpiresTime);
    }

    /**
     * 保存数据
     *
     * @param key         键
     * @param value
     * @param expiresTime 过期时间 /s
     */
    public void set(String key, String value, long expiresTime) {
        Jedis jedis = null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.set(key,value,"NX","EX",expiresTime);
        } catch (Exception ex) {
            logger.error("保存数据失败:key:{}\nvalue:{}\n",key,value, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 保存数据
     * 注:Object类对象将会JSON序列化为String存储为String类型
     *
     * @param key   键
     * @param value JAVABean对象
     */
    public void set(String key, Object value) {
        set(key,value,defaultExpiresTime);
    }

    /**
     * 保存数据
     * 注：Object类对象将会JSON序列化为String存储为String类型,任何改动均是对象完全序列化反序列化操作,故性能较低
     *
     * @param key         键
     * @param value       JAVABean对象
     * @param expiresTime 过期时间 /s
     */
    public void set(String key, Object value, long expiresTime) {

    }

    /**
     * 保存高性能简单Map结构数据
     * 注：将使用Redis Hash方式存储
     *
     * @param key   键
     * @param value 简单Map结构数据
     */
    public CacheMapData set(String key, Map<String,String> value) {
        set(key,value,defaultExpiresTime);
        return null;
    }

    /**
     * 保存高性能简单Map结构数据
     * 注：将使用Redis Hash方式存储
     *
     * @param key         键
     * @param value       简单Map结构数据
     * @param expiresTime 过期时间 /s
     */
    public CacheMapData set(String key, Map<String,String> value, long expiresTime) {
        CacheMapData cacheMapData = new CacheMapDataImpl(getRealKey(key),pool);
        cacheMapData.createInWebCache(value);
        setExpire(key,expiresTime);
        return cacheMapData;
    }

    /**
     * 保存高性能简单List结构数据
     * 注：将使用Redis List方式存储
     *
     * @param key         键
     * @param value       简单List结构数据
     */
    public CacheListData set(String key, List<String> value){
        return set(key,value,defaultExpiresTime);
    }

    /**
     * 保存高性能简单List结构数据
     * 注：将使用Redis List方式存储
     *
     * @param key         键
     * @param value       简单List结构数据
     * @param expiresTime 过期时间 /s
     */
    public CacheListData set(String key, List<String> value, long expiresTime) {
        // 实例化缓存列表型数据对象
        CacheListData cacheListData = new CacheListDataImpl(getRealKey(key),pool);
        cacheListData.createInWebCache(value);
        setExpire(key,expiresTime);
        return cacheListData;
    }

    /**
     * 获取数据
     *
     * @param key 键
     */
    public String getStringData(String key) {
        key =  getRealKey(key);
        String value = null;
        Jedis jedis = null;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception ex) {
            logger.error("获取缓存中数据异常:RealKey:{} Value:{}", key, value, ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return value;
    }

    /**
     * 获取高性能简单List数据对象
     *
     * @param key 键
     * @return 简单List结构数据
     */
    public CacheListData getListData(String key) {
        // 实例化缓存列表型数据对象
        CacheListData cacheListData = new CacheListDataImpl(getRealKey(key),pool);
        return cacheListData;
    }

    /**
     * 获取高性能简单Map数据
     *
     * @param key 键
     * @return CacheMapData 简单Map结构数据
     */
    public CacheMapData getMapData(String key) {
        CacheMapData cacheMapData = new CacheMapDataImpl(getRealKey(key),pool);
        return cacheMapData;
    }


//    public String get(String key){
//        String value = null;
//        Jedis jedis = null;
//        try {
//            jedis = pool.getResource();
//            value = jedis.get(key);
//            Map map = new HashMap<String,Object>();
//            map.put("field1", "field1-value");
//            map.put("field2", "field2-value");
//
//
////            map.put("ceshi", Arrays.asList("AS大","AS大","测试啊"));
//            jedis.hmset("key1",map);
//            jedis.expire("key1",60);
//            //返回哈希表key中所有域和值
//            Map<String,String> map1 = jedis.hgetAll("key1");
//            for(Map.Entry entry: map1.entrySet()) {
//                System.out.print(entry.getKey() + ":" + entry.getValue() + "\n");
//            }
//            System.out.println();
//
////            jedis
//        } catch (Exception e) {
//            logger.error("获取数据失败",e);
//        } finally {
//            jedis.close();
//        }
//        logger.debug("Key:{} Value:{}",key,value);
//        return value;
//    }


}
