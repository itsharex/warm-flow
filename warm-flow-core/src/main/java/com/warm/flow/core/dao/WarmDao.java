package com.warm.flow.core.dao;

import com.warm.flow.core.orm.agent.WarmQuery;
import com.warm.flow.core.utils.page.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseMapper接口
 *
 * @author warm
 * @date 2023-03-17
 */
public interface WarmDao<T> {

    T newEntity();

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return 实体
     */
    T selectById(Serializable id);

    /**
     * 根据ids查询
     *
     * @param ids 主键
     * @return 实体
     */
    List<T> selectByIds(Collection<? extends Serializable> ids);

    /**
     * 分页查询
     *
     * @param entity 实体列表
     * @return 集合
     */
    Page<T> selectPage(T entity, Page<T> page);

    /**
     * 分页查询
     *
     * @param entity 实体列表
     * @param query
     * @return 集合
     */
    List<T> selectList(T entity, WarmQuery<T> query);

    /**
     * 查询数量
     *
     * @param entity 实体列表
     * @return 集合
     */
    long selectCount(T entity);

    /**
     * 新增
     *
     * @param entity 实体
     * @return 结果
     */
    int save(T entity);

    /**
     * 根据id修改
     *
     * @param entity 实体
     * @return 结果
     */
    int updateById(T entity);

    /**
     * 根据entity删除
     *
     * @param entity 实体
     * @return 结果
     */
    int delete(T entity);

    /**
     * 根据id删除
     *
     * @param id 主键
     * @return 结果
     */
    int deleteById(Serializable id);

    /**
     * 根据ids批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Collection<? extends Serializable> ids);

    /**
     * 批量新增
     *
     * @param list 集合
     */
    void saveBatch(List<T> list);

    /**
     * 批量修改
     *
     * @param list 集合
     */
    void updateBatch(List<T> list);
}
