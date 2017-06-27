package pro.zkey.core.cache;


import java.util.List;
import java.util.Map;

/**
 * 缓存
 * @date 2017-06-20 10:28:53
 * @author PS
 */
public interface Cache {

    /**
     * 获取缓存服务器中真实Key
     * @param key 业务key
     * @return 真实Key
     */

    String getRealKey(String key);

    /**
     * 设置缓存超时时间
     * @param key
     * @param expiresTime
     */
    void setExpire(String key,long expiresTime);
    /**
     * 保存数据
     * @param key 键
     * @param value
     */
    void set(String key, String value);

    /**
     * 保存数据
     *
     * @param key 键
     * @param value
     * @param expiresTime 过期时间 /s
     */
    void set(String key, String value, long expiresTime);

    /**
     * 保存数据
     * 注:Object类对象将会JSON序列化为String存储为String类型
     *
     * @param key 键
     * @param value JAVABean对象
     */
    void set(String key, Object value);

    /**
     * 保存数据
     * 注：Object类对象将会JSON序列化为String存储为String类型,任何改动均是对象完全序列化反序列化操作,故性能较低
     *
     * @param key 键
     * @param value JAVABean对象
     * @param expiresTime 过期时间 /s
     */
    void set(String key, Object value, long expiresTime);

    /**
     * 保存高性能简单Map结构数据
     * 注：将使用Redis Hash方式存储
     *
     * @param key 键
     * @param value 简单Map结构数据
     */
    CacheMapData set(String key, Map<String,String> value);

    /**
     * 保存高性能简单Map结构数据
     * 注：将使用Redis Hash方式存储
     *
     * @param key 键
     * @param value 简单Map结构数据
     * @param expiresTime 过期时间 /s
     */
    CacheMapData set(String key, Map<String,String> value, long expiresTime);

    /**
     * 保存高性能简单List结构数据
     * 注：将使用Redis List方式存储
     *
     * @param key 键
     * @param value 简单List结构数据
     */
    CacheListData set(String key, List<String> value);

    /**
     * 保存高性能简单List结构数据
     * 注：将使用Redis List方式存储
     *
     * @param key 键
     * @param value 简单List结构数据
     * @param expiresTime 过期时间 /s
     */
    CacheListData set(String key, List<String> value, long expiresTime);


    /**
     * 获取数据
     * @param key 键
     */
    String getStringData(String key);

    /**
     * 获取高性能简单List数据
     * @param key 键
     * @return 简单List结构数据
     */
    CacheListData getListData(String key);

    /**
     * 获取高性能简单Map数据
     * @param key 键
     * @return CacheMapData 简单Map结构数据
     */
    CacheMapData getMapData(String key);
}
