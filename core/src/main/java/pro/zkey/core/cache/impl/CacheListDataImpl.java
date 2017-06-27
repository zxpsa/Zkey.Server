package pro.zkey.core.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.zkey.core.cache.CacheListData;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * 简单列表
 * 2017-06-20 13:13:41
 *
 * @author PS
 */
public class CacheListDataImpl implements CacheListData {
    // 日志
    private Logger logger = LoggerFactory.getLogger(CacheListDataImpl.class);
    // 设置该对象对应的Web缓存key
    // 所有操作均基于该key进行
    private String key = "";
    // jedis链接池
    private JedisPool pool = null;
    // 过期时间 /s
//    private long expiresTime;
    // 是否因数据更新原因自动延期
//    private boolean isRenew;

    /**
     * 构造高性能简单List结构数据对象
     *
     * @param realKey 在缓存中真实Key
     * @param pool    链接池
     */
    public CacheListDataImpl(String realKey, JedisPool pool) {
        this.key = realKey;
        this.pool = pool;
    }

    /**
     * 在Web缓存中创建实例
     *
     * @param list 初始化数据
     */
    public void createInWebCache(List<String> list) {
        Jedis jedis = null;
        String[] values = list.toArray(new String[list.size()]);
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            jedis.del(key);
            if (list.size() == 0) {
                // 若传入数据为空时依然创建Key,传入一占位值,立刻删除
                values = new String[]{"__default"};
                jedis.rpush(key, values);
                jedis.lpop(key);
            } else {
                jedis.rpush(key, values);
            }
        } catch (Exception ex) {
            logger.error("在Web缓存中创建实例失败:List>\n", list, ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 批量添加到列表尾部
     *
     * @param values
     * @return 修改后的元素个数
     */
    public long push(String... values) {
        Jedis jedis = null;
        long rs = 0;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            rs = jedis.rpush(key, values);
        } catch (Exception ex) {
            logger.error("获取缓存中数据异常:RealKey:{} Value:{}", key, values, ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 批量添加到列表尾部
     *
     * @param list
     */
    public void push(List<String> list) {
        push(list.toArray(new String[list.size()]));
    }

    /**
     * 添加数据到列表头部
     *
     * @param values
     */
    public long pushToTop(String... values) {
        Jedis jedis = null;
        long rs = 0;
        try {
            // 从连接池中获取jedis实例
            jedis = pool.getResource();
            rs = jedis.lpush(key, values);
        } catch (Exception ex) {
            logger.error("获取缓存中数据异常:RealKey:{} Value:{}", key, values, ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 返回元素个数
     *
     * @return 元素个数
     */
    public long size() {
        Jedis jedis = null;
        long rs = 0;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            rs = jedis.llen(key);
        } catch (Exception ex) {
            logger.error("返回元素个数Error:\n", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 列表是否为空
     * 即:元素数量为0
     *
     * @return
     */
    public boolean isEmpty() {
        Jedis jedis = null;
        boolean rs = false;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            // 不存在直接说明为Empty
            if (!jedis.exists(key)) {
                rs = true;
            } else {
                long count = jedis.llen(key);
                if (count == 0) {
                    rs = true;
                }
            }
        } catch (Exception ex) {
            logger.error("返回元素个数Error:\n", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 返回数组数据
     *
     * @return
     */
    public String[] toArray() {
        String[] rs;
        List<String> list = toArrayList();
        rs = list.toArray(new String[list.size()]);
        return rs;
    }

    /**
     * 返回原生列表型数据
     *
     * @return
     */
    public List<String> toArrayList() {
        Jedis jedis = null;
        List<String> rs;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            rs = jedis.lrange(key, 0, -1);
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 删除所有匹配到的元素
     *
     * @param e
     * @return 是否删除成功
     */
    public long del(String e) {
        Jedis jedis = null;
        long rs;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            rs = jedis.lrem(key, 0, e);
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }

    /**
     * 删除列表中所有元数
     */
    public void clear() {
        Jedis jedis = null;
        String rs;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            if (jedis.exists(key)) {
                rs = jedis.ltrim(key, 0, 0);
                if ("OK".equals(rs.toUpperCase())) {
                    jedis.rpop(key);
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 修改
     *
     * @param index   索引
     * @param element
     */
    public void set(long index, String element) {
        Jedis jedis = null;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            jedis.lset(key, index, element);
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    /**
     * 删除最后一个元素,返回最后一个元素
     */
    public String pop() {
        Jedis jedis = null;
        String rs;
        try {
            // 从连接池中获取Jedis实例
            jedis = pool.getResource();
            rs = jedis.rpop(key);
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        } finally {
            if (jedis != null) jedis.close();
        }
        return rs;
    }
}
