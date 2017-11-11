package lava.bluepay.com.dbtest.sqlite;

/**
 * 规划所有的数据库操作
 */

public interface IBaseDao<T> {

    /**
     * 插入
     * @param entity
     * @return
     */
    long insert(T entity);
}
