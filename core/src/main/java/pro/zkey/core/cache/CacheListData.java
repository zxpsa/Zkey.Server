package pro.zkey.core.cache;

import java.util.List;

/**
 * 缓存列表类型数据
 * 2017-06-21 11:03:02
 * @author PS
 */
public interface CacheListData{

    /**
     * 在Web缓存中创建实例
     * @param list 初始化数据
     */
    void createInWebCache(List<String> list);
    /**
     * 添加到列表尾部
     * @param values
     * @return 元素个数
     */
    long push(String... values);

    /**
     * 批量添加到列表尾部
     * @param list
     * @return 元素个数
     */
    void push(List<String> list);

    /**
     * 添加数据到列表头部
     * @param values
     * @return 返回总元素个数
     */
    long pushToTop(String... values);

    /**
     * 返回元素个数
     * @return 元素个数
     */
    long size();

    /**
     * 列表是否为空
     * 即:元素数量为0
     * @return
     */
    boolean isEmpty();


    /**
     * 返回数组
     * @return
     */
    String[] toArray();

    /**
     * 返回原生列表型数据
     * @return
     */
    List<String> toArrayList();

    /**
     * 删除所有匹配到的元素
     * @param e
     * @return 删除的元素个数
     */
    long del(String e);

    /**
     * 删除列表中所有元数
     */
    void clear();

    /**
     * 修改
     * @param index 索引
     * @param element
     */
    void set(long index, String element);

    /**
     * 删除最后一个元素,返回最后一个元素
     */
    String pop();

}
